package com.shop.shoppify.controller;

import com.shop.shoppify.dto.request.SizeDTO;
import com.shop.shoppify.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/size")
public class SizeController {

    @Autowired
    SizeService sizeService;

    @GetMapping("/all")
    public ResponseEntity<List<SizeDTO>> getAllSizes(@RequestParam("type") String type) {
        List<SizeDTO> sizes = sizeService.getSizesByType(type);
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeDTO> getSize(@PathVariable("id") int id) {
        SizeDTO size = sizeService.getSizeById(id);
        return new ResponseEntity<>(size, HttpStatus.OK);
    }

}
