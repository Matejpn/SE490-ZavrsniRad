package com.shop.shoppify.controller;

import com.shop.shoppify.dto.request.MessageDto;
import com.shop.shoppify.dto.request.PurchaseRequestDTO;
import com.shop.shoppify.dto.response.PurchaseResponseDTO;
import com.shop.shoppify.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<PurchaseResponseDTO> addPurchase(@RequestBody PurchaseRequestDTO purchaseDTO,
            Principal principal) {
        PurchaseResponseDTO purchase = purchaseService.addPurchase(purchaseDTO, principal.getName());
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<PurchaseResponseDTO> getPurchase(@PathVariable("id") int id, Principal principal) {
        PurchaseResponseDTO purchase = purchaseService.getPurchaseById(id, principal.getName());
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<PurchaseResponseDTO>> getMyPurchases(Principal principal) {
        List<PurchaseResponseDTO> purchases = purchaseService.getMyPurchases(principal.getName());
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PurchaseResponseDTO>> getUserPurchases(@PathVariable("id") int id) {
        List<PurchaseResponseDTO> purchases = purchaseService.getUserPurchases(id);
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases() {
        List<PurchaseResponseDTO> purchases = purchaseService.getAllPurchases();
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageDto> confirmPurchase(@PathVariable("id") int id) {
        purchaseService.confirmPurchase(id);
        return new ResponseEntity<>(new MessageDto("Success"), HttpStatus.OK);
    }
}