package org.example.store;

import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.example.store.dao.EsGoodsDao;
import org.example.store.dao.GoodsDao;
import org.example.store.po.EsGoods;
import org.example.store.pojo.Goods;
import org.example.store.service.EsGoodsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EsTest {
    @Resource
    private EsGoodsDao esGoodsDao;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private EsGoodsService esGoodsService;
    @Test
    void searchFromEs() {
        Page<EsGoods> goodsPage = esGoodsDao.findByTitle("iphone", PageRequest.of(1, 10));
        int size = goodsPage.getSize();
//        List<EsGoods> aa = esGoodsDao.findByClassification("手机");
//        System.out.println(aa);
        goodsPage.forEach(System.out::println);
        System.out.println(goodsPage.getContent());

        System.out.println("current size:" + size + " total:" + goodsPage.getTotalElements());

        System.out.println("---------------------");
//        Iterable<EsGoods> all = esGoodsDao.findAll();
        Page<EsGoods> all= esGoodsDao.findByClassification("手机", PageRequest.of(1, 10));
        all.forEach(System.out::println);

    }
    @Test
    @DisplayName("导入数据到ES")
    public void importDataToES() {
        // 查询出所有数据库中的商品数据
        List<Goods> goods = goodsDao.findAll();
        goods.forEach(good -> {
            EsGoods esGoods = new EsGoods();
            esGoods.setId(good.getId());
            esGoods.setTitle(good.getTitle());
            esGoods.setPicture(good.getPhoto());
            esGoods.setPrice(good.getUnit_price().doubleValue());
            esGoods.setClassification(good.getClassification());
            esGoods.setSales(good.getSales());
            // 将数据保存到es
            esGoodsDao.save(esGoods);
            System.out.println("入库成功 " + good.getTitle());
        });
    }

    @Test
    public void getList(){
//        Sort order = Sort.by(new Sort.Order(Sort.Direction.DESC, "sales"));
//        esGoodsDao.findAll(order).forEach(System.out::println);
//        esGoodsService.goodsIndex().forEach(System.out::println);
        Page<EsGoods> byClassification = esGoodsDao.findByClassification("电视",PageRequest.of(1,10));
        List<EsGoods> es=esGoodsDao.findByClassification("电视");
        System.out.println(es);
        byClassification.forEach(System.out::println);
        System.out.println(byClassification.getContent());
        System.out.println(byClassification.getTotalElements());
    }
}
