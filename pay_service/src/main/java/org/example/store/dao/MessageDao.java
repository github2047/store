package org.example.store.dao;


import org.example.store.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
    @Query(value = "from Message order by id desc")
    Message findLast();
}
