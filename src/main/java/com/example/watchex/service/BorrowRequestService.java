package com.example.watchex.service;

import com.example.watchex.entity.BorrowRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BorrowRequestService extends GenericService<BorrowRequest, Integer> {
    Page<BorrowRequest> get(int page);

    BorrowRequest show(Integer id);
}
