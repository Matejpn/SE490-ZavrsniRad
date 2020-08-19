package com.shop.shoppify.service;

import com.shop.shoppify.dto.request.SizeDTO;

import java.util.List;

public interface SizeService {
    List<SizeDTO> getAllSizes();

    List<SizeDTO> getSizesByType(String type);

    SizeDTO getSizeById(int id);
}
