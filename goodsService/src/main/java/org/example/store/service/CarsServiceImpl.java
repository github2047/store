package org.example.store.service;

import org.example.store.dao.CarsDao;
import org.example.store.mapper.GoodsMapper;
import org.example.store.pojo.Cars;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarsServiceImpl implements CarsService{
    @Resource
    private CarsDao carsDao;
    @Resource
    private GoodsMapper mapper;
    @Override
    public void save(Cars cars) {
        carsDao.save(cars);
    }

    @Override
    public void deleteById(Integer id) {
        carsDao.deleteById(id);
    }

    @Override
    public Cars findByGidAndUid(Integer gid,String uid) {
        return mapper.findByGidAAndUid(gid,uid);
    }

    @Override
    public List<Cars> findListByUid(String uid) {
        return mapper.findListByUid(uid);
    }

    @Override
    public void updateNumber(Integer number, Integer id) {
        mapper.updateNumber(number,id);
    }
}
