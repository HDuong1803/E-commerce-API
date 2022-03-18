package com.bezkoder.springjwt.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSizeCountReq {

    private Integer sizeId;
    private int size;
    private int count;
    
}
