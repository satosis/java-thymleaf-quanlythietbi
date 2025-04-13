package com.example.watchex.repository;

import com.example.watchex.dto.TransactionRevenueDto;
import com.example.watchex.entity.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Integer> {
    @Query("select p from BorrowRequest p where p.status = :status")
    List<BorrowRequest> getByStatus(String status);

  @Query(nativeQuery = true, value =
            "select sum(cast(p.id as int)) as totalMoney, DATE(p.created_at) as day from borrow_requests p " +
                    "where p.status = :status and MONTH(p.created_at) = :month group by day")
    List<TransactionRevenueDto> getTotalIdsGroupedByCreatedAt(String status, Integer month);

}
