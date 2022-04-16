package org.example.store.service;

import org.example.store.pojo.Goods;

import java.util.List;
import java.util.Optional;

public interface GoodService {
    List<Goods> findAll();
    Optional<Goods> findById(Integer id);
    List<String> listClass();
    void save(Goods goods);
    void updateSales(Integer sales,Integer id);
}
