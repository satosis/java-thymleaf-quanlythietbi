package com.example.watchex.service;

import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.repository.BorrowHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowHistoryService {
    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    public void save(BorrowHistory borrowHistory) {
        borrowHistoryRepository.save(borrowHistory);
    }
    public BorrowHistory findByBorrowRequest(BorrowRequest borrowRequest) {
        return borrowHistoryRepository.findByBorrowRequest(borrowRequest);
    }
}
