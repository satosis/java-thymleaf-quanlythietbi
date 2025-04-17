package com.example.watchex.repository;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Integer> {
    @Query("SELECT p FROM BorrowHistory p " +
            "WHERE (p.borrowRequest = :#{#borrowRequest} )")
    BorrowHistory findByBorrowRequest(BorrowRequest borrowRequest);

    @Query("SELECT p FROM BorrowHistory p " +
            "WHERE (p.devices = :#{#devices} )")
    BorrowHistory findByDevices(Devices devices);

    @Query("SELECT p FROM BorrowHistory p " +
            " join Devices dv on p.devices = dv " +
            "WHERE (dv.name LIKE %:#{#dto.getName()}% or :#{#dto.getName()} is null or :#{#dto.getName()} = '') and " +
            "(p.statusDevice = :#{#dto.getStatus()} or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '') and" +
            "(p.user.id = :#{#dto.getUser()} or :#{#dto.getUser()} is null ) and" +
            "(p.id = :#{#dto.getId()} or :#{#dto.getId()} is null ) ")
    Page<BorrowHistory> search(SearchDto dto, Pageable pageable);

}
