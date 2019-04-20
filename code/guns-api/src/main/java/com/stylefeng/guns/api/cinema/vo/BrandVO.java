package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVO implements Serializable {

    private String brandId;
    private String brandName;
    private boolean isActive;


}
