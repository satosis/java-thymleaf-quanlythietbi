package com.example.watchex.service;

import com.example.watchex.entity.MaintenanceRecords;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaintenanceRecordsService extends GenericService<MaintenanceRecords, Integer> {
    Page<MaintenanceRecords> get(int page);

    MaintenanceRecords show(Integer id);
    List<MaintenanceRecords> getAll(int page);
}
