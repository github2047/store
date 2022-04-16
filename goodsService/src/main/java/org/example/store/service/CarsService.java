package org.example.store.service;


import org.example.store.pojo.Cars;

import java.util.List;

public interface CarsService {
    void save(Cars cars);
    void deleteById(Integer id);
    Cars findByGidAndUid(Integer gid,String uid);
    List<Cars> findListByUid(String uid);
    void updateNumber(Integer number,Integer id);
}
