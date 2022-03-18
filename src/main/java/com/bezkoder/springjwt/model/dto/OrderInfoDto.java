package com.bezkoder.springjwt.model.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto {

    
    private Integer id;

    private long totalPrice;
    private int size;
    private String productName;
    private long productPrice;
    
}
