package org.example.store.service;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.example.store.dao.EsGoodsDao;
import org.example.store.po.EsGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EsGoodsServiceImpl implements EsGoodsService{
    @Resource
    private EsGoodsDao esGoodsDao;
    @Override
    public Page<EsGoods> findByClassification(String classification, Pageable pageable) {
        return esGoodsDao.findByClassification(classification,pageable);
    }

    @Override
    public Page<EsGoods> findByTitle(String title, Pageable pageable) {
        return esGoodsDao.findByTitle(title,pageable);
    }

    @Override
    public void save(EsGoods esGoods) {
        esGoodsDao.save(esGoods);
    }

    @Override
    public List<EsGoods> goodsIndex() {
        Sort order = Sort.by(new Sort.Order(Sort.Direction.DESC, "sales"));
        Iterable<EsGoods> all = esGoodsDao.findAll(order);
        return (List<EsGoods>) all;
    }

    @Override
    public List<EsGoods> findByClassification(String classification) {
        return esGoodsDao.findByClassification(classification);
    }


}
