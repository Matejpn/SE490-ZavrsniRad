package com.shop.shoppify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;

    private Double price;

    private String gender;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<Item> items;

    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;

    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.MERGE
    )
    private Set<ProductSize> productSizes = new HashSet<>();


    public void addSize(Size size, int count) {
        ProductSize productSize = new ProductSize(this, size, count);
        productSizes.add(productSize);
        size.getProductSizes().add(productSize);
    }
}