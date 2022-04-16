package org.example.store.controller;

import lombok.val;
import org.example.store.pojo.Goods;
import org.example.store.pojo.Orders;
import org.example.store.service.GoodService;
import org.example.store.service.OrderService;
import org.example.store.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin//允许跨域
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private GoodService goodService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setGoodService(GoodService goodService) {
        this.goodService = goodService;
    }

    @PostMapping("/save")
    public ApiResponse add(@RequestBody Orders orders){
        ApiResponse data=new ApiResponse();
        if(orders==null||orders.equals("")){
            return data.setCode(1).setMessage("数据异常");
        }
        orders.setCreate_time(new Date());
        orders.setCreate_by(orders.getUid());
        orders.setStatus(0);
        orderService.save(orders);
        Goods good = goodService.findById(orders.getGid()).get();
        good.setStock(good.getStock()-orders.getNumber());
        good.setUpdate_time(new Date());
        good.setUpdate_by(orders.getUid());
        goodService.save(good);
        return data.setMessage("ok");
    }
    @PostMapping("/updateStatus")
    public ApiResponse updateStatus(Integer status,String id){
        ApiResponse data=new ApiResponse();
        if(status==null||id.equals("")){
            return data.setCode(1).setMessage("数据异常");
        }
        if(status==2){
            Orders order = orderService.findById(id);
            Goods good = goodService.findById(order.getGid()).get();
            good.setStock(good.getStock()+order.getNumber());
            good.setUpdate_by(order.getUid());
            good.setUpdate_time(new Date());
            goodService.save(good);
            orderService.updateStatus(status,id);
        }else{
            orderService.updateStatus(status,id);
        }
        return data.setMessage("ok").setData(orderService.findById(id));
    }
    @GetMapping("/getAll")
    public ApiResponse getOrders(String uid){
        ApiResponse data=new ApiResponse();
        if(uid==null||uid.equals("")){
            return data.setCode(1).setMessage("数据异常");
        }
        List<Orders> listByUid = orderService.findListByUid(uid);
        return data.setMessage("ok").setData(listByUid);
    }
    @GetMapping("/getByStatus")
    public ApiResponse getByStatus(String uid){
        ApiResponse data=new ApiResponse();
        if(uid==null||uid.equals("")){
            return data.setCode(1).setMessage("数据异常");
        }
        List<Orders> listByStatus = orderService.findListByStatus(uid);
        return data.setMessage("ok").setData(listByStatus);
    }
    @RequestMapping("/updatePayTimeById")
    public ApiResponse updatePayTimeById(String id){
        ApiResponse data=new ApiResponse();
        Date pay_time=new Date();
        if(id==null||id.equals("")){
            return data.setCode(1).setMessage("数据异常");
        }
        System.out.println(pay_time);
        System.out.println(id);
        orderService.updatePayTime(pay_time,id);

        return data.setMessage("ok");
    }
}
