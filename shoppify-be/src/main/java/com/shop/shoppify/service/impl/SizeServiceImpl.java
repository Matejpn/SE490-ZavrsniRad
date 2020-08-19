package com.shop.shoppify.service.impl;

import com.shop.shoppify.dto.request.SizeDTO;
import com.shop.shoppify.model.Size;
import com.shop.shoppify.repository.SizeRepository;
import com.shop.shoppify.service.SizeService;
import com.shop.shoppify.util.ShoppifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    SizeRepository sizeRepository;

    @Override
    public List<SizeDTO> getAllSizes() {
        List<Size> sizes = sizeRepository.findAll();
        return sizes.stream()
                .map(SizeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<SizeDTO> getSizesByType(String type) {
        List<Size> sizes = sizeRepository.findByType(type);
        return sizes.stream()
                .map(SizeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public SizeDTO getSizeById(int id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Size not exist!"));
        return new SizeDTO(size);
    }
}
