package com.example.watchex.repository;

import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<BorrowRequest, Integer> {
    @Query("select c from BorrowRequest c where c.product_id = :product")
    List<BorrowRequest> findByProduct(Devices devices);
}
