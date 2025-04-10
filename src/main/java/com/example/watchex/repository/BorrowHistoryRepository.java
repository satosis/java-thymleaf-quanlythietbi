package com.example.watchex.repository;

import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Integer> {
    @Query("SELECT p FROM BorrowHistory p " +
            "WHERE (p.borrowRequest = :#{#borrowRequest} )")
    BorrowHistory findByBorrowRequest(BorrowRequest borrowRequest);

}
