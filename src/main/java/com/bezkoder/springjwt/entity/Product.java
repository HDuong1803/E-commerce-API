package com.bezkoder.springjwt.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bezkoder.springjwt.model.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "products")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "slug")
	private String slug;

	@Column(name = "description")
	private String description;

	@Column(name = "detail")
	private String detail;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "price")
	private float price;

	@Column(name = "count_buy")
	private int count_buy;
	@Column(name = "active")
	private int active;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User user;

	@Column(name = "created_at")
	private Date created_at;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToMany
	@JoinTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "size_id"))
	private List<ProductSize> sizes = new ArrayList<>();

	public Product() {
	}

	public ProductDto toDto() {
		return ProductDto.builder()
				.id(id)
				.name(name)
				.description(description)
				.detail(detail)
				.price(price)
				.build();

	}

}
