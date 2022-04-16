package org.example.store.service;

import org.example.store.po.EsGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface EsGoodsService {
    Page<EsGoods> findByClassification(String classification, Pageable pageable);
    Page<EsGoods> findByTitle(String title, Pageable pageable);
    void save(EsGoods esGoods);
    List<EsGoods> goodsIndex();
    List<EsGoods> findByClassification(String classification);
}
