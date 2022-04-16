package org.example.store.dao;

import org.example.store.po.EsGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsGoodsDao extends ElasticsearchRepository<EsGoods,Integer> {
    List<EsGoods> findByClassification(String title);
    Page<EsGoods> findByClassification(String classification, Pageable pageable);
    Page<EsGoods> findByTitle(String title, Pageable pageable);
}
