package com.shop.shoppify.repository;

import com.shop.shoppify.model.Purchase;
import com.shop.shoppify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findAllByUserOrderByDateDesc(User user);

    List<Purchase> findAllByOrderByDateDesc();

}
