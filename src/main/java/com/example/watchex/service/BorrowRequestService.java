package com.example.watchex.service;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.dto.TransactionRevenueDto;
import com.example.watchex.entity.BorrowRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowRequestService extends GenericService<BorrowRequest, Integer> {
    Page<BorrowRequest> get(SearchDto dto);

    BorrowRequest show(Integer id);

    List<BorrowRequest> getByStatus(String status);

    List<TransactionRevenueDto> getTotalIdsGroupedByCreatedAt(String status);

}
