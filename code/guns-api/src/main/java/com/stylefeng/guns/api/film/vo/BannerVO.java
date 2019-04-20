package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerVO implements Serializable {

    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;

    // getter 和 setter方法，toString方法

}
