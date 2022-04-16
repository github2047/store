package org.example.store.controller.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.example.store.pojo.ApiResponse;
import org.example.store.pojo.Userinfo;
import org.example.store.pojo.biz.LoginData;
import org.example.store.pojo.biz.RegData;
import org.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends ApiController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(
            value = "用户登录", httpMethod = "POST", notes = "账号登录",
            response = Userinfo.class
    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(required = true, name = "username", value = "账号", dataType = "String"),
//            @ApiImplicitParam(required = true, name = "password", value = "密码", dataType = "String")
//    })
    public ApiResponse login(@Validated @RequestBody LoginData loginData) {
        log.info("data:{}", loginData.toString());
        Userinfo userinfo = userService.login(loginData.getUsername(), loginData.getPassword());
        return ApiResponse.Success(userinfo);
    }

    @PostMapping("/reg")
    @ApiOperation(
            value = "用户注册", httpMethod = "POST", notes = "用户注册",
            response = Userinfo.class
    )
    public Userinfo reg(@Validated @RequestBody RegData regData) {
        log.info("data:{}", regData.toString());
        Userinfo user = new Userinfo();
        user.setPassword(regData.getPassword())
                .setPayPwd(regData.getPayPwd())
                .setEmail(regData.getUsername());
        if (!userService.save(user)) {
            throw new RuntimeException("创建用户失败了");
        }
        return user;
    }

    @GetMapping("exists")
    public boolean exists(String email) {
        try {
            Userinfo userinfo = userService.getByName(email);
            return userinfo != null;
        } catch (Exception e) {
            return true;
        }
    }

    @RequestMapping("/query")
    @ApiOperation(
            value = "根据名称查询用户信息", httpMethod = "POST", notes = "根据名称查询用户信息1",
            response = Userinfo.class
    )
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "name", value = "电子邮箱或者手机号码", dataType = "String"),
    })
    public ApiResponse getByName(String name) {
        if (!StringUtils.hasLength(name)) {
            return ApiResponse.Error(1, "查询的名称不能为空");
        }
        Userinfo userinfo = userService.getByName(name);
        if (null != userinfo) {
            userinfo.setPassword(null).setPayPwd(null);
        }
        return ApiResponse.Success(userinfo);
    }

    @RequestMapping("/charge")
    public Userinfo charge(Integer id, Double money) {
        if (money == null || money <= 0) {
            throw new IllegalArgumentException("充值的金额不正确");
        }
        Long m = new Double(money * 100).longValue();
        return userService.charge(id, m);

    }
}
