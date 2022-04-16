package org.example.store.controller;

import org.example.store.po.EsGoods;
import org.example.store.pojo.Cars;
import org.example.store.pojo.Goods;
import org.example.store.service.CarsService;
import org.example.store.service.EsGoodsService;
import org.example.store.service.GoodService;
import org.example.store.vo.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin//允许跨域
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private EsGoodsService esGoodsService;
    @Resource
    private GoodService goodService;
    @Resource
    private CarsService carsService;

    @RequestMapping("/updateEsGoodsInfo")
    public ApiResponse updateEsGoodsInfo(){
        ApiResponse data=new ApiResponse();
        List<Goods> goods = goodService.findAll();
        goods.forEach(good -> {
            EsGoods esGoods = new EsGoods();
            esGoods.setId(good.getId());
            esGoods.setTitle(good.getTitle());
            esGoods.setPicture(good.getPhoto());
            esGoods.setPrice(good.getUnit_price().doubleValue());
            esGoods.setClassification(good.getClassification());
            esGoods.setSales(good.getSales());
            // 将数据保存到es
            esGoodsService.save(esGoods);
        });
        return data.setMessage("数据更新成功").setData(goods);
    }

    @RequestMapping("/findByTitle")
    public ApiResponse findByTitle(@RequestParam("title") String title,@RequestParam(name = "page", defaultValue = "1") Integer page,
                                   @RequestParam(name = "size", defaultValue = "10") Integer size){
        ApiResponse date=new ApiResponse();
        if(page<=0) page=1;
        if(title.equals("")||title==null){
            return date.setCode(1).setMessage("抱歉，没有找到“"+title+"”相关商品");
        }
        Page<EsGoods> esGoods=esGoodsService.findByTitle(title, PageRequest.of(page-1, size));
        if (esGoods.getContent().isEmpty()){
            return date.setCode(1).setMessage("抱歉，没有找到“"+title+"”相关商品");
        }
        return date.setData(esGoods).setMessage("获取成功");
    }

    @RequestMapping("/findByClassification")
    public ApiResponse findByClassification(@RequestParam("classification") String classification,@RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size){
        ApiResponse date=new ApiResponse();
        if(page<=0) page=1;
        if(classification.equals("")||classification==null){
            return date.setCode(1).setMessage("抱歉，没有找到“"+classification+"”相关分类");
        }
        Page<EsGoods> esGoods = esGoodsService.findByClassification(classification,PageRequest.of(page-1,size));
        if (esGoods.getContent()==null||esGoods.getTotalElements()==0){
            return date.setCode(1).setMessage("抱歉，没有找到“"+classification+"”相关分类");
        }
        return date.setData(esGoods).setMessage("获取成功");
    }

    @RequestMapping("/getGoodsClass")
    public ApiResponse getGoodsClass(){
        ApiResponse data=new ApiResponse();
        List<String> classification = goodService.listClass();
        return data.setData(classification).setMessage("分类list");
    }
    @GetMapping("/goodsIndex")
    public ApiResponse goodsIndex(){
        ApiResponse data=new ApiResponse();
        List<EsGoods> esGoods = esGoodsService.goodsIndex();
        return data.setData(esGoods);
    }
    @GetMapping("/goodInfo")
    private ApiResponse findGoodById(Integer id){
        ApiResponse data=new ApiResponse();
        if(id==null||id.equals("")){
            return data.setCode(1).setMessage("异常，请重新进入");
        }
        Optional<Goods> good = goodService.findById(id);
        data.setData(good);
        return data;
    }
    @RequestMapping("/addCar")
    private ApiResponse addCar(@RequestBody Cars cars){
        ApiResponse data=new ApiResponse();
        if(cars==null){
            return data.setCode(1).setMessage("加入购物车异常");
        }
        Cars byGid = carsService.findByGidAndUid(cars.getGid(),cars.getUid());
        if(byGid!=null){
           cars.setId(byGid.getId()).setNumber(byGid.getNumber()+cars.getNumber());
        }
        cars.setId(-((int) new Date().getTime()));
        carsService.save(cars);
        return data.setMessage("加入成功");
    }
    @RequestMapping("/deleteCar")
    private ApiResponse deleteCar(Integer id){
        ApiResponse data=new ApiResponse();
        if(id==null){
            return data.setCode(1).setMessage("删除异常");
        }
        carsService.deleteById(id);
        return data.setMessage("删除成功");
    }
    @RequestMapping("/findCarByUid")
    private ApiResponse findCarByUid(String uid){
        ApiResponse data=new ApiResponse();
        if(uid==null){
            return data.setCode(1).setMessage("异常");
        }
        List<Cars> listByUid = carsService.findListByUid(uid);
        return data.setData(listByUid).setMessage("ok");
    }
    @RequestMapping("/updateNumber")
    private ApiResponse updateNumber(Integer number,Integer id){
        ApiResponse data=new ApiResponse();
        if(number==null ||id==null){
            return data.setCode(1).setMessage("异常");
        }
       carsService.updateNumber(number,id);
        return data.setMessage("ok");
    }
    @RequestMapping("/updateSales")
    private ApiResponse updateSales(Integer sales,Integer id){
        ApiResponse data=new ApiResponse();
        if(sales==null ||id==null){
            return data.setCode(1).setMessage("异常");
        }
        goodService.updateSales(sales,id);
        return data.setMessage("ok");
    }
}
