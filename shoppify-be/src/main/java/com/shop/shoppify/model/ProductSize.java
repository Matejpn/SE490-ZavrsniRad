package com.shop.shoppify.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_has_size")
public class ProductSize implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
	private ProductSizePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false, nullable = false)
    private Product product;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", insertable = false, updatable = false, nullable = false)
    private Size size;
 
    @Column(name = "count")
    int count;

    public ProductSize(Product product, Size size, int count) {
        this.product = product;
        this.size = size;
        this.count = count;
        this.id = new ProductSizePK(product.getId(), size.getId());
    }
}