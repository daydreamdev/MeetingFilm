package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDetailVO implements Serializable {

    private String filmId;
    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;
    private InfoRequstVO info04;


}
