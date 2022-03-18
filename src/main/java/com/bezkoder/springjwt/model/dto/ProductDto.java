package com.bezkoder.springjwt.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private String detail;
    private float price;
}
