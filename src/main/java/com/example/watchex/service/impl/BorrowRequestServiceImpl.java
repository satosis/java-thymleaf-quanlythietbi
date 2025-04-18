package com.example.watchex.service.impl;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.dto.TransactionRevenueDto;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.repository.BorrowRequestRepository;
import com.example.watchex.service.BorrowRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowRequestServiceImpl extends GenericServiceImpl<BorrowRequest, Integer> implements BorrowRequestService {

    @Autowired
    private BorrowRequestRepository repository;

    public Page<BorrowRequest> get(SearchDto dto) {
        return repository.search(dto, PageRequest.of(dto.getPageIndex(), dto.getPageSize()));
    }

    @Override
    public BorrowRequest show(Integer id) {
        Optional<BorrowRequest> result = repository.findById(id);
        return result.orElse(null);
    }

    public List<BorrowRequest> getByStatus(String status) {
        return repository.getByStatus(status);
    }

    @Override
    public List<TransactionRevenueDto> getTotalIdsGroupedByCreatedAt(String status){

        Integer month = Calendar.getInstance().get(Calendar.MONTH)+1;
        return repository.getTotalIdsGroupedByCreatedAt(status, month);
    }

    @Override
    public void deleteByDevice(Integer id) {
        repository.deleteByDeviceId(id);
    }
}
