package com.example.watchex.repository;

import com.example.watchex.dto.ConfirmBorrowRequestDto;
import com.example.watchex.entity.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Integer> {
    @Query("select p from BorrowRequest p ")
    List<BorrowRequest> getByStatus(Integer status);

}
