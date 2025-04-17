package com.example.watchex.service.impl;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.MaintenanceRecords;
import com.example.watchex.repository.MaintenanceRecordsRepository;
import com.example.watchex.service.MaintenanceRecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordsServiceImpl extends GenericServiceImpl<MaintenanceRecords, Integer> implements MaintenanceRecordsService {

    @Autowired
    private MaintenanceRecordsRepository repository;

    public Page<MaintenanceRecords> get(SearchDto dto) {
        return repository.search(dto, PageRequest.of(dto.getPageIndex(), dto.getPageSize()));
    }

    @Override
    public MaintenanceRecords show(Integer id) {
        Optional<MaintenanceRecords> result = repository.findById(id);
        return result.orElse(null);
    }

    public List<MaintenanceRecords> getAll(int page) {
        return repository.findAll();
    }

}
