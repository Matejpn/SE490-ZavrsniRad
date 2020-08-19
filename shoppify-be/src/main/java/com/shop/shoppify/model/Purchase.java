package com.shop.shoppify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total_price")
    private Double totalPrice;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_confirmed", columnDefinition = "boolean default false")
    private Boolean isConfirmed;

}