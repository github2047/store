package org.example.store.dao;

import org.elasticsearch.common.recycler.Recycler;
import org.example.store.pojo.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsDao extends JpaRepository<org.example.store.pojo.Cars,Integer> {

}
