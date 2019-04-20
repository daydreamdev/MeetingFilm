package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaVO implements Serializable {

    private String areaId;
    private String areaName;
    private boolean isActive;


}
