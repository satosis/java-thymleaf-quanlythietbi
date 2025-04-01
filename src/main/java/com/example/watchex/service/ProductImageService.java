package com.example.watchex.service;

import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository repository;

    public Page<BorrowRequest> get(int page) {
        return repository.findAll(PageRequest.of(page - 1, 10, Sort.by("id").descending()));
    }

    public List<BorrowRequest> getAll() {
        return repository.findAll();
    }

    public void save(BorrowRequest productimage) {
        repository.save(productimage);
    }

    public BorrowRequest show(Integer id) throws ClassNotFoundException {
        Optional<BorrowRequest> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ClassNotFoundException("BorrowRequest not found");
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<BorrowRequest> findByProduct(Devices devices) {
        return repository.findByProduct(devices);
    }

}
