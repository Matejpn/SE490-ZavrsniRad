package com.shop.shoppify.repository;

import com.shop.shoppify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByIsDeletedFalse();

    @Query(value = "SELECT * FROM shoppify.product AS s " +
            "WHERE (:priceFrom is NULL or s.price >= :priceFrom) " +
            "AND (:priceTo is NULL or s.price <= :priceTo) " +
            "AND (:dateFrom IS NULL OR s.release_date >= :dateFrom) " +
            "AND (:dateTo IS NULL OR s.release_date <= :dateTo) " +
            "AND (:idCategory is NULL or s.category_id = :idCategory) " +
            "AND (:idBrand is NULL or s.brand_id = :idBrand) " +
            "AND s.is_deleted = false " +
            "ORDER BY release_date DESC; ",
    nativeQuery = true)
    List<Product> findAllFiltered(@Param("idCategory") String idCategory, @Param("idBrand") String idBrand, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("priceFrom") String priceFrom, @Param("priceTo") String priceTo);

    @Query(value = "SELECT IFNULL(SUM(quantity)*COUNT(*),0) AS popularity FROM shoppify.item where product_id = :productId", nativeQuery = true)
    int getPopularityByProductId(@Param("productId") int productId);
    
    @Modifying
    @Query(value = "DELETE FROM shoppify.product_has_size WHERE product_id=:productId", nativeQuery = true)
    void removeProductSizesByProductId(@Param("productId") int productId);

    @Modifying
    @Query(value = "UPDATE shoppify.product_has_size SET count=:count WHERE product_id=:productId and size_id=:sizeId", nativeQuery = true)
    void changeProductSizeCount(@Param("productId") int productId, @Param("sizeId") int sizeId, @Param("count") int count);

}
