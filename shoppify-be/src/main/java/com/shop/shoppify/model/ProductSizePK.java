package com.shop.shoppify.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the product_has_size database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ProductSizePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id", insertable = false, updatable = false, nullable = false)
	private int productId;

	@Column(name = "size_id", insertable = false, updatable = false, nullable = false)
	private int sizeId;

	public ProductSizePK(int productId, int sizeId) {
		this.productId = productId;
		this.sizeId = sizeId;
	}

}