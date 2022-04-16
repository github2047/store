package org.example.store.dao;

import org.example.store.pojo.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MerchantDao extends JpaRepository<Merchant, Integer> {
    /**
     * 使用id和密码查询商户信息
     *
     * @param id
     * @param password
     * @return
     */
    Optional<Merchant> findByIdAndPassword(Integer id, String password);
    Optional<Merchant> findByAccountAndPassword(String account, String password);

    /**
     * 查询商户
     * @param appName
     * @return
     */
    Optional<Merchant> findByAppName(String appName);

    /**
     * 资金增加
     * @param id
     * @param balance
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true,value = "update merchant set balance=balance + :balance where id=:id")
    int increaseBalance(@Param("id") Integer id,@Param("balance") Long balance);

    /**
     * 资金减少
     * @param id
     * @param balance
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true,value = "update merchant set balance=balance - :balance where id=:id where balace > :balance")
    int decreaseBalance(@Param("id") Integer id,@Param("balance") Long balance);
}
