package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearVO implements Serializable {

    private String yearId;
    private String yearName;
    private boolean isActive;

}
