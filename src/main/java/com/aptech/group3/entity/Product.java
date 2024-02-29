package com.aptech.group3.entity;

import org.springframework.context.annotation.Bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
	@Column(nullable = false)
   private String name;
	@Column(nullable = false)
   private String sku;
	@Column(nullable = false)
   private Double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public Product(String name, String sku, Double price) {
		super();
		this.name = name;
		this.sku = sku;
		this.price = price;
	}
	
	public Product() {
		super();
	}
	
	
	
	
}
