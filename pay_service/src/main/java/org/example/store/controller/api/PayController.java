package org.example.store.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.example.store.pojo.ApiResponse;
import org.example.store.pojo.PayRecord;
import org.example.store.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController extends ApiController {
    private PayService payService;

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @GetMapping("/record")
    @ApiOperation(
            value = "查询用户交易记录", httpMethod = "GET", notes = "查询用户交易记录1",
            response = PayRecord.class
    )
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "uid", value = "要查询的用户编号", dataType = "Integer"),
            @ApiImplicitParam(required = true, name = "page", value = "查询页码", dataType = "Integer"),
            @ApiImplicitParam(required = true, name = "size", value = "每页查询条数", dataType = "Integer")
    })
    public ApiResponse recordList(
            Integer uid,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "15") Integer size) {
        IPage<PayRecord> payRecords = payService.selectList(uid, page, size);
        return ApiResponse.Success(payRecords);
    }
}
