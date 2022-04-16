package org.example.store.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.SneakyThrows;
import org.example.store.pojo.Merchant;
import org.example.store.pojo.PayRecord;
import org.example.store.pojo.biz.MerchantLogin;
import org.example.store.pojo.biz.MerchantReg;
import org.example.store.pojo.biz.RsaKey;
import org.example.store.service.MerchantService;
import org.example.store.service.PayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Resource
    private MerchantService merchantService;

    @Resource
    private PayService payService;

    @PostMapping("create")
    public Merchant create(@Validated @RequestBody MerchantReg data) {
        return merchantService.create(
                data.getPassword(),
                data.getAppName(),
                data.getAccount()
        );
    }

    @PostMapping("login")
    public Merchant login(@Validated @RequestBody MerchantLogin data) {
        return merchantService.login(
                data.getAccount(),
                data.getPassword()
        );
    }

    @SneakyThrows
    @GetMapping("create-key")
    public RsaKey createKey(Integer id) {
        Thread.sleep(5000);
        return merchantService.generateRsaKey(id);
    }

    @PostMapping("update-icon")
    public Merchant updateIcon(@RequestBody Map<String,String> data) {
        return merchantService.update(Integer.valueOf(data.get("id")), m -> m.setAppIcon(data.get("icon")));
    }

    @GetMapping("exists")
    public boolean exists(Merchant merchant) {
        return merchantService.exists(merchant);
    }

    @GetMapping("query")
    public Merchant query(Integer id) {
        Merchant merchant = merchantService.findById(id);
        if (null == merchant) throw new RuntimeException("商户信息不存在");
        return merchant;
    }

    @GetMapping("records")
    public IPage<PayRecord> records(Integer id,
                                    @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                    @RequestParam(name = "page", defaultValue = "1", required = false) Integer page) {
        return payService.selectListByMerchant(id, page, size);
    }
}
