package org.example.store.service;

import org.example.store.dao.SecKillDao;
import org.example.store.mapper.SecMapper;
import org.example.store.po.SecKillGoods;
import org.example.store.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SecKillServiceImpl implements SecKillService{
    private SecKillDao secKillDao;
    private OrderService orderService;
    private SecMapper secMapper;
    @Autowired
    public void setSecKillDao(SecKillDao secKillDao) {
        this.secKillDao = secKillDao;
    }
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setSecMapper(SecMapper secMapper) {
        this.secMapper = secMapper;
    }

    @Override
    @Transactional // 启用事务
    public String secKill(Orders orders) {
        // 使用jpa查询商品信息
        Optional<SecKillGoods> goodsOptional = secKillDao.findById(orders.getGid());
        // 判断商品是否存在
        if (!goodsOptional.isPresent()) {
            throw new RuntimeException("秒杀的商品不存在");
        }
        SecKillGoods secKillGoods = goodsOptional.get();
        // 判断库存是否小于购买数量
        if (secKillGoods.getStock() == 0) {
            throw new RuntimeException("商品库存不足1");
        }
        if(orders.getNumber()>1){
            return "秒杀商品限购一个";
        }
        orders.setCreate_by(orders.getUid());
        orders.setCreate_time(new Date());
        orders.setStatus(0);
        // 创建订单
        // 保存订单数据
        orderService.save(orders);
        // 可能有问题？
//        goods.setStock(goods.getStock() - count); // 重新设置库存 -- 没有锁 所以抛弃
//        goodsDao.save(goods); // 更新商品库存
        // 直接在mysql中减
        // update goods set stock = stock - 数量 where id=编号
        // 执行减库存 并获取受影响行数
        int resultCount = secKillDao.deductCount(orders.getGid()); // 直接在数据库的基础上 进行库存减少
        // 只有库存不够 才不会真正执行更新操作 所以受影响为0
        if (resultCount < 1) {
            throw new RuntimeException("商品库存不足 => " + resultCount);
        }
        return orders.getId();
    }

    @Override
    public List<SecKillGoods> findAll() {
        return secKillDao.findAll();
    }

    @Override
    public SecKillGoods findById(Integer id) {
        return secKillDao.findById(id).get();
    }

    @Override
    public void updateSales(Integer sales, Integer id) {
        secMapper.updateSales(sales,id);
    }
}
