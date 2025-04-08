package com.example.watchex.service.impl;

import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.repository.BorrowRequestRepository;
import com.example.watchex.service.BorrowRequestService;
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
public class BorrowRequestServiceImpl extends GenericServiceImpl<BorrowRequest, Integer> implements BorrowRequestService {

    @Autowired
    private BorrowRequestRepository repository;

    public Page<BorrowRequest> get(int page) {
        return repository.findAll(PageRequest.of(page - 1, 10, Sort.by("id").descending()));
    }

    @Override
    public BorrowRequest show(Integer id) {
        Optional<BorrowRequest> result = repository.findById(id);
        return result.orElse(null);
    }


}
