package org.example.store.dao;

import org.example.store.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDao extends JpaRepository<Orders,String> {
}