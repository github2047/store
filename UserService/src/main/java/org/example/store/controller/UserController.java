package org.example.store.controller;

import com.google.gson.Gson;
import org.example.store.pojo.User;
import org.example.store.pojo.UserToken;
import org.example.store.service.SendMailService;
import org.example.store.service.UserService;
import org.example.store.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin//允许跨域
@RequestMapping("/user")
public class UserController {
    @Value("${application.url}")
    private String applicationUrl;

    /**
     * 文件访问地址
     */
    @Value("${application.upload.url}")
    private String uploadBaseUrl;
    /**
     * 文件保存地址
     */
    @Value("${application.upload.dir}")
    private String uploadDir;
    private UserService userService;
    private SendMailService sendMailService;
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setSendMailService(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @RequestMapping("/all")
    public ApiResponse all(){
        ApiResponse data=new ApiResponse();
        data.setData(userService.findAll());
        return data;
    }
    @GetMapping("/findById")
    public ApiResponse findById(String account){
        ApiResponse data=new ApiResponse();
        if (account.equals("")||account==null){
            return data.setCode(1).setMessage("异常，请重新进入");
        }
        User user = userService.findAccountOrEmail(account);
        return data.setData(user);
    }
    @RequestMapping("/update")
    public ApiResponse update(@RequestBody User user){
        ApiResponse data=new ApiResponse();
        if (user.getAccount()==null){
            return data.setCode(1).setMessage("异常，请重新进入");
        }
        userService.update(user);
        UserToken token=new UserToken();
        token.setUser(user);
        token.creatToken();
        stringRedisTemplate.delete(token.getToken());
        return data.setMessage("修改成功");
    }
    @PostMapping("/login")
    public ApiResponse login(String account,String password){
        ApiResponse data=new ApiResponse();
        if (account==null||account.equals("")||password==null||password.equals("")){
            data.setCode(1).setMessage("输入的消息异常，请重新填写！");
        }
        User user = userService.findAccountOrEmail(account);
        if(user.getStatus()!=1){
            return data.setCode(1).setMessage("账号已冻结，请联系管理员（2412601628@qq.com）");
        }
        if (user==null || !user.getPassword().equals(password)){
            return data.setCode(1).setMessage("账号/邮箱或者密码错误！！");
        }
        UserToken token=new UserToken();
        token.setUser(user);
        token.creatToken();
        Gson gson=new Gson();
        stringRedisTemplate.opsForValue().set(token.getToken(), gson.toJson(token), 1, TimeUnit.DAYS);
        token.getUser().setPassword(null);
        data.setCode(0).setData(token).setMessage("登录成功！欢迎您："+user.getNick());
        return data;
    }
    @GetMapping("/verify_loginInfo")
    public ApiResponse verify_loginInfo(String token){
        ApiResponse data=new ApiResponse();
        String u = stringRedisTemplate.opsForValue().get(token);
        if(u==null||u.equals("")){
            return data.setCode(1).setMessage("未登录或登录已失效，请重新登录！！！");
        }
        Gson gson=new Gson();
        UserToken userToken = gson.fromJson(u, UserToken.class);
        userToken.getUser().setPassword(null);
        data.setCode(0).setData(userToken);
        return data;
    }
    @GetMapping("/del_loginInfo")
    public String del_loginInfo(String token){
        stringRedisTemplate.delete(token);
        return "xxx";
    }
    @RequestMapping("/create")
    public ApiResponse save(@RequestBody User user){
        ApiResponse data=new ApiResponse();
        if (user.getEmail().equals("") ||user.getEmail()==null || user.getPassword().equals("")||user.getPassword()==null) {
            return data.setCode(1).setMessage("邮箱和密码异常，请重新填写！");
        }
        User accountOrEmail = userService.findAccountOrEmail(user.getEmail());
        if(accountOrEmail!=null){
            data.setCode(1).setMessage("邮箱已存在，请更换或者用此邮箱登录！！");
            return data;
        }
        StringBuffer account = new StringBuffer();
        User user1;
         do {
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
                account.append(r.nextInt(10));
            }
            user1 = userService.findAccountOrEmail(account.toString());
            if (user1!=null){
                account=null;
            }else {
                break;
            }
        }while(true);
        user.setAccount(account.toString());
        user.setCreate_time(new Date());
        user.setStatus(1);
        user.setLevel(0);
        userService.save(user);
        user.setPassword(null);
        data.setData(user).setMessage("注册成功！！");
        return data;
    }
    @RequestMapping("/send_code")
    public ApiResponse sendCode(String email) throws MessagingException {
        ApiResponse data=new ApiResponse();
        if (StringUtils.isEmpty(email)) {
            return data.setCode(1).setMessage("邮箱地址为空");
        }
        User accountOrEmail = userService.findAccountOrEmail(email);
        if (accountOrEmail!=null){
            return data.setCode(1).setMessage("邮箱地址已存在");
        }
        StringBuffer code = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(r.nextInt(10));
        }
        // 保存code到redis 过期时间设为 3min
        if (sendMailService.sendEmail(email, code.toString())) {
            // 保存code到redis 过期时间设为 3min
            stringRedisTemplate.opsForValue().set("code:" + email, code.toString(), 15, TimeUnit.MINUTES);
            return data.setMessage("验证码发送成功，请注意查看"+email+"邮箱！！");
        }
        return data.setCode(2).setMessage("发送邮件失败");
    }
    @RequestMapping("/verify_code")
    public ApiResponse verifyCode(String email,String code){
        ApiResponse data=new ApiResponse();
        String verifyCode = stringRedisTemplate.opsForValue().get("code:" + email);
        if(!verifyCode.equals(code)){
            return data.setCode(1).setMessage("验证码不正确，请认真核对后输入，或者重新发送！！");
        }
        if(verifyCode==null){
            return data.setCode(1).setMessage("验证码已过期，请重新获取！！");
        }
        return data;
    }
    @PostMapping("/upload")
    public ApiResponse<Map<String, String>> uploadImages(@RequestParam("file") MultipartFile file) throws IOException {
        ApiResponse<Map<String, String>> data = new ApiResponse();
        try {
            byte[] bytes = file.getBytes(); // 获取所有文件内容
            // 后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            // 获取要保存文件的目录
            String realPath = ResourceUtils.getURL("classpath:static/images").getPath();
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            // 完整的保存路径
            String path = uploadBaseUrl + fileName;
            Map<String, String> uploadFile = new HashMap<>();
            uploadFile.put("url", applicationUrl + path);
            uploadFile.put("path", path);

            // 保存文件
//            boolean flag = UploadFileUtil.uploadFile(bytes, realPath, fileName);
            File targetFile = new File(realPath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            try {
                FileOutputStream out = new FileOutputStream(targetFile +"/"+ fileName);
                out.write(bytes);
                out.flush();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            //返回数据
            data.setData(uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(-1);
            //返回异常
            data.setMessage("上传图片时IO异常:" + e.getMessage());
            return data;

        }
        return data;
    }
}
