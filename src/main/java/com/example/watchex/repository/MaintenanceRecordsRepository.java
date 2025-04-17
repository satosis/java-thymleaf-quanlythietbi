package com.example.watchex.repository;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.MaintenanceRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface MaintenanceRecordsRepository extends JpaRepository<MaintenanceRecords, Integer> {
    @Query("select p from MaintenanceRecords p ")
    List<MaintenanceRecords> getByStatus(Integer status);

    @Query("SELECT p FROM MaintenanceRecords p " +
            " join Devices dv on p.devices = dv " +
            "WHERE (dv.name LIKE %:#{#dto.getName()}% or :#{#dto.getName()} is null or :#{#dto.getName()} = '') and " +
            "(p.maintenance_status = :#{#dto.getStatus()} or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '') and" +
            "(p.maintenanceUser.id = :#{#dto.getUser()} or :#{#dto.getUser()} is null ) and" +
            "(p.reportedUser.id = :#{#dto.getReporter()} or :#{#dto.getReporter()} is null ) and" +
            "(p.id = :#{#dto.getId()} or :#{#dto.getId()} is null ) ")
    Page<MaintenanceRecords> search(SearchDto dto, Pageable pageable);

}
