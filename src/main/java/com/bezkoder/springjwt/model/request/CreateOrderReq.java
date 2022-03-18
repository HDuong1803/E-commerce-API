package com.bezkoder.springjwt.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CreateOrderReq {

    
    private Integer productId;
    private int size;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private long totalPrice;
    private long productPrice;
    
}
