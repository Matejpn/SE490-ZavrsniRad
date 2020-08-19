package com.shop.shoppify.repository;

import java.util.List;

import com.shop.shoppify.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    List<Size> findByType(String type);

    @Modifying
    @Query(value = "INSERT INTO shoppify.product_has_size (`count`, `size_id`, `product_id`) VALUES (:count, :sizeId, :productId)", nativeQuery = true)
    void saveProductSize(@Param("sizeId") int sizeId, @Param("productId") int productId, @Param("count") int count);
}
