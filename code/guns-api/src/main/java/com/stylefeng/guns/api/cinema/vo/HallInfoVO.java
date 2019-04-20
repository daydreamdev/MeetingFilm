package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVO implements Serializable {

    private String hallFieldId;
    private String hallName;
    private String price;
    private String seatFile;
    // 已售座位必须关联订单才能查询
    private String soldSeats;

}
