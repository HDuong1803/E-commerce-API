package com.bezkoder.springjwt.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductReq {

    private String name;
	private String description;
	private String detail;
	private float price;
    private Integer categoryId;
	private int size;
	
    
}
