package org.example.store.service;

import org.example.store.dao.OrderDao;
import org.example.store.mapper.OrderMapper;
import org.example.store.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao;
    private OrderMapper orderMapper;
    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public void save(Orders orders) {
        orderDao.save(orders);
    }

    @Override
    public void updateStatus(Integer status, String id) {
        orderMapper.updateStatus(status,id);
    }

    @Override
    public void updatePayTime(Date pay_time, String id) {
        orderMapper.updatePayTime(pay_time,id);
    }

    @Override
    public List<Orders> findListByUid(String uid) {
        return orderMapper.findListByUid(uid);
    }

    @Override
    public Orders findById(String id) {
        return orderDao.findById(id).get();
    }

    @Override
    public List<Orders> findListByStatus(String uid) {
        return orderMapper.findListByStatus(uid);
    }
}
