package com.shop.shoppify.service;

import com.shop.shoppify.dto.request.PurchaseRequestDTO;
import com.shop.shoppify.dto.response.PurchaseResponseDTO;

import java.util.List;

public interface PurchaseService {

    PurchaseResponseDTO addPurchase(PurchaseRequestDTO purchaseDTO, String email);

    PurchaseResponseDTO getPurchaseById(int id, String email);

    List<PurchaseResponseDTO> getMyPurchases(String email);

    List<PurchaseResponseDTO> getUserPurchases(int id);

    List<PurchaseResponseDTO> getAllPurchases();

    void confirmPurchase(int id);

}
