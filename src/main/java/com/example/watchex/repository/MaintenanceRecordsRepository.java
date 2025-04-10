package com.example.watchex.repository;

import com.example.watchex.entity.MaintenanceRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordsRepository extends JpaRepository<MaintenanceRecords, Integer> {
    @Query("select p from MaintenanceRecords p ")
    List<MaintenanceRecords> getByStatus(Integer status);

}
