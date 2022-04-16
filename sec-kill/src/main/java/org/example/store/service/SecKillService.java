package org.example.store.service;

import org.apache.ibatis.annotations.Update;
import org.example.store.po.SecKillGoods;
import org.example.store.pojo.Orders;

import java.util.List;

public interface SecKillService {
    String secKill(Orders orders);
    List<SecKillGoods> findAll();
    SecKillGoods findById(Integer id);
    void updateSales(Integer sales,Integer id);
}
