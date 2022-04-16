package org.example.store.dao;

import org.example.store.po.SecKillGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SecKillDao extends JpaRepository<SecKillGoods,Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update sec_kill_goods set stock=stock - 1 where id=?1 and stock >= 1")
    int deductCount(@Param("id") Integer id);
}
