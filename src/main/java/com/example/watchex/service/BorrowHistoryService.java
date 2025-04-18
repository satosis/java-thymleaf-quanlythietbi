package com.example.watchex.service;

import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.repository.BorrowHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.watchex.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class BorrowHistoryService {
    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    public Page<BorrowHistory> get(SearchDto dto) {
        return borrowHistoryRepository.search(dto, PageRequest.of(dto.getPageIndex(), dto.getPageSize()));
    }

    public void save(BorrowHistory borrowHistory) {
        borrowHistoryRepository.save(borrowHistory);
    }

    public BorrowHistory findByBorrowRequest(BorrowRequest borrowRequest) {
        return borrowHistoryRepository.findByBorrowRequest(borrowRequest);
    }

    public BorrowHistory findByDevices(Devices devices) {
        return borrowHistoryRepository.findByDevices(devices);
    }
}
