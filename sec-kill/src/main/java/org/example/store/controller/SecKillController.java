package org.example.store.controller;

import com.sun.deploy.association.utility.AppConstants;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.store.po.SecKillGoods;
import org.example.store.pojo.Orders;
import org.example.store.service.SecKillService;
import org.example.store.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sec_kill")
@CrossOrigin // 允许跨域
@Slf4j
public class SecKillController {
    private SecKillService secKillService;
@Autowired
    public void setSecKillService(SecKillService secKillService) {
        this.secKillService = secKillService;
    }

    // 一开始应该把秒杀的商品数据放缓存
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
    //初始化缓存数据
    @PostConstruct // 对应controller的void方法只调用一次
    public void initCacheData() {
        secKillService.findAll().forEach(g -> {
            log.info("对 {} 进行缓存 库存为 {}", g.getTitle(), g.getStock());
            // 对商品库存进行缓存
            stringRedisTemplate.opsForValue().set("goods_stock_" + g.getId(),
                    g.getStock().toString());
        });
    }

    // 存储已经卖完的商品信息
    private static ConcurrentHashMap<Integer,Boolean> stockOutMap = new ConcurrentHashMap<>();


    @RequestMapping("/kill")
    public ApiResponse secKill(@RequestBody Orders orders) {
        ApiResponse data=new ApiResponse();
        // 分布式锁
        if(stockOutMap.get(orders.getId()) != null){
            return data.setCode(1).setMessage("fail: 已经卖完了");
        }

        String cacheKey = "goods_stock_" + orders.getGid();
//         对应redis的数据进行jian - 1，并返回减法后的结果
        long count = stringRedisTemplate.opsForValue().decrement(cacheKey);
        if (count < 0) {
            // 此时库存没有了 , 保存到已买完的对象
            stockOutMap.put(orders.getGid(),true);
            log.info("stock count ===>" + count);
            // 对缓存进行库存 + 1
            stringRedisTemplate.opsForValue().increment(cacheKey); //
            return data.setCode(1).setMessage("fail:库存不足");
        }


        try {
            String orderId = secKillService.secKill(orders);
            if (orderId == null) {
                return data.setCode(1).setMessage("fail");
            } else {
                return data.setCode(0).setMessage("success");
            }
        } catch (Exception e) {
            //如果数据库下单失败 则 需要对缓存进行还原
            stringRedisTemplate.opsForValue().increment(cacheKey); //
            return data.setCode(1).setMessage("秒杀失败:" + e.getMessage());
        }
    }
    @RequestMapping("/findAll")
    public ApiResponse findAll(){
        ApiResponse data=new ApiResponse();
        List<SecKillGoods> all = secKillService.findAll();
        return data.setCode(0).setData(all);
    }
    @RequestMapping("/findById")
    public ApiResponse findById(Integer id){
        ApiResponse data=new ApiResponse();
        SecKillGoods secKillGoods= secKillService.findById(id);
        return data.setCode(0).setData(secKillGoods);
    }
    @RequestMapping("/updateSales")
    private ApiResponse updateSales(Integer sales,Integer id){
        ApiResponse data=new ApiResponse();
        if(sales==null ||id==null){
            return data.setCode(1).setMessage("异常");
        }
        secKillService.updateSales(sales,id);
        return data.setMessage("ok");
    }
}
