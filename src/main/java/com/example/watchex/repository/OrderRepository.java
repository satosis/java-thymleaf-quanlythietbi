package com.example.watchex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT p FROM Order p " +
            "WHERE p.od_transaction_id = :transaction " +
            "order by p.createdAt desc")
    List<Order> getByTransaction(Transaction transaction);
}
