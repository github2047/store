package org.example.store.service;

import org.example.store.dao.GoodsDao;
import org.example.store.mapper.GoodsMapper;
import org.example.store.pojo.Goods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodService{
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public List<Goods> findAll() {
        return goodsDao.findAll();
    }

    @Override
    public Optional<Goods> findById(Integer id) {
        return goodsDao.findById(id);
    }

    @Override
    public List<String> listClass() {
        return goodsMapper.listClass();
    }

    @Override
    public void save(Goods goods) {
        goodsDao.save(goods);
    }

    @Override
    public void updateSales(Integer sales, Integer id) {
        goodsMapper.updateSales(sales,id);
    }
}
