package org.example.store.service;

import org.example.store.pojo.Orders;

import java.util.Date;
import java.util.List;

public interface OrderService {
    void save(Orders orders);
    void updateStatus(Integer status,String id);
    void updatePayTime(Date pay_time, String id);
    List<Orders> findListByUid(String uid);
    Orders findById(String id);
    List<Orders> findListByStatus(String uid);
}
