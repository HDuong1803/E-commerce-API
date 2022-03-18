package com.bezkoder.springjwt.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "size")
public class ProductSize {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    
    @Column(name = "size")
    private int size;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    
    public ProductSize() {
    }


    public ProductSize(Integer id, int size, int quantity) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;
    }

    @ManyToMany(mappedBy = "sizes")
    private List<Product> products = new ArrayList<>();

}
