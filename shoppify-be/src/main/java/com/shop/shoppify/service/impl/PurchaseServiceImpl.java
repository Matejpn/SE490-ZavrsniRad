package com.shop.shoppify.service.impl;

import com.shop.shoppify.dto.request.ItemRequestDTO;
import com.shop.shoppify.dto.request.PurchaseRequestDTO;
import com.shop.shoppify.dto.response.PurchaseResponseDTO;
import com.shop.shoppify.enums.RoleEnum;
import com.shop.shoppify.model.Item;
import com.shop.shoppify.model.Product;
import com.shop.shoppify.model.ProductSize;
import com.shop.shoppify.model.Purchase;
import com.shop.shoppify.model.Size;
import com.shop.shoppify.model.User;
import com.shop.shoppify.repository.ItemRepository;
import com.shop.shoppify.repository.ProductRepository;
import com.shop.shoppify.repository.PurchaseRepository;
import com.shop.shoppify.repository.SizeRepository;
import com.shop.shoppify.repository.UserRepository;
import com.shop.shoppify.service.PurchaseService;
import com.shop.shoppify.util.ShoppifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Override
    @Transactional
    public PurchaseResponseDTO addPurchase(PurchaseRequestDTO purchaseDTO, String email) {
        User user = userRepository.findByEmail(email);
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setDate(new Date());
        purchase.setIsConfirmed(false);
        purchaseRepository.save(purchase);
        List<Item> items = new ArrayList<>();
        double totalPrice = 0;
        for (ItemRequestDTO itemDTO : purchaseDTO.getItems()) {
            Item item = new Item();
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Product don't exist!"));
            List<ProductSize> productSizes = product.getProductSizes().stream().filter(
                    ps -> ps.getId().getProductId() == product.getId() && ps.getId().getSizeId() == itemDTO.getSizeId())
                    .collect(Collectors.toList());

            if (productSizes.size() < 1) {
                throw new ShoppifyException(HttpStatus.BAD_REQUEST, "One of the product has wrong size!");
            }
            ProductSize productSize = productSizes.get(0);
            if (productSize.getCount() < itemDTO.getQuantity()) {
                throw new ShoppifyException(HttpStatus.BAD_REQUEST, "There is not enough of one of the products!");
            }
            productRepository.changeProductSizeCount(product.getId(), itemDTO.getSizeId(),
                    productSize.getCount() - itemDTO.getQuantity());
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPurchase(purchase);
            Size size = sizeRepository.findById(itemDTO.getSizeId())
                    .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Size don't exist!"));
            item.setSize(size.getValue());
            items.add(item);
            totalPrice += product.getPrice() * itemDTO.getQuantity();
        }
        itemRepository.saveAll(items);
        purchase.setTotalPrice(totalPrice);
        purchase.setItems(items);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", user.getFirstName() + " " + user.getLastName());
        paramMap.put("email", user.getEmail());
        paramMap.put("address", user.getAddress() + ", " + user.getCity());
        paramMap.put("postcode", user.getPostalCode());
        paramMap.put("phone", user.getPhoneNumber());
        paramMap.put("purchaseDate", new SimpleDateFormat("dd/MM/yyyy").format(purchase.getDate()));
        paramMap.put("totalPrice", purchase.getTotalPrice().toString());
        paramMap.put("items", purchase.getItems());

        SendEmail.sendPurchaseEmail("matejwebshop@gmail.com", "", "", "New Purchase", paramMap);
        return new PurchaseResponseDTO(purchase);
    }

    @Override
    public PurchaseResponseDTO getPurchaseById(int id, String email) {
        User user = userRepository.findByEmail(email);
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Purchase dont exist!"));
        if (user.getRole().getId() == RoleEnum.USER.getValue() && user.getId() != purchase.getUser().getId()) {
            throw new ShoppifyException(HttpStatus.FORBIDDEN, "Can not see other user purchases!");
        }
        return new PurchaseResponseDTO(purchase);
    }

    @Override
    public List<PurchaseResponseDTO> getMyPurchases(String email) {
        User user = userRepository.findByEmail(email);
        List<Purchase> purchases = purchaseRepository.findAllByUserOrderByDateDesc(user);
        return purchases.stream().map(PurchaseResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseResponseDTO> getUserPurchases(int id) {
        User user = userRepository.getOne(id);
        List<Purchase> purchases = purchaseRepository.findAllByUserOrderByDateDesc(user);
        return purchases.stream().map(PurchaseResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseResponseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAllByOrderByDateDesc();
        return purchases.stream().map(PurchaseResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void confirmPurchase(int id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Purchase don't exist!"));
        if (purchase.getIsConfirmed()) {
            throw new ShoppifyException(HttpStatus.NOT_FOUND, "Purchase already confirmed!");
        }
        purchase.setIsConfirmed(true);
        purchaseRepository.save(purchase);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", purchase.getUser().getFirstName() + " " + purchase.getUser().getLastName());
        paramMap.put("email", purchase.getUser().getEmail());
        paramMap.put("address", purchase.getUser().getAddress() + ", " + purchase.getUser().getCity());
        paramMap.put("postcode", purchase.getUser().getPostalCode());
        paramMap.put("phone", purchase.getUser().getPhoneNumber());
        paramMap.put("purchaseDate", new SimpleDateFormat("dd/MM/yyyy").format(purchase.getDate()));
        paramMap.put("totalPrice", purchase.getTotalPrice().toString());
        paramMap.put("items", purchase.getItems());

        SendEmail.sendPurchaseEmail(purchase.getUser().getEmail(), "admin@mailinator.com", "", "Purchase Confirmed",
                paramMap);
    }

}
