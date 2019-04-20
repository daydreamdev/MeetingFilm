package com.stylefeng.guns.rest.common.persistence.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author jiangzh
 * @since 2018-09-20
 */
@TableName("mooc_order_t")
public class MoocOrderT extends Model<MoocOrderT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 影院编号
     */
    @TableField("cinema_id")
    private Integer cinemaId;
    /**
     * 放映场次编号
     */
    @TableField("field_id")
    private Integer fieldId;
    /**
     * 电影编号
     */
    @TableField("film_id")
    private Integer filmId;
    /**
     * 已售座位编号
     */
    @TableField("seats_ids")
    private String seatsIds;
    /**
     * 已售座位名称
     */
    @TableField("seats_name")
    private String seatsName;
    /**
     * 影片售价
     */
    @TableField("film_price")
    private Double filmPrice;
    /**
     * 订单总金额
     */
    @TableField("order_price")
    private Double orderPrice;
    /**
     * 下单时间
     */
    @TableField("order_time")
    private Date orderTime;
    /**
     * 下单人
     */
    @TableField("order_user")
    private Integer orderUser;
    /**
     * 0-待支付,1-已支付,2-已关闭
     */
    @TableField("order_status")
    private Integer orderStatus;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getSeatsIds() {
        return seatsIds;
    }

    public void setSeatsIds(String seatsIds) {
        this.seatsIds = seatsIds;
    }

    public String getSeatsName() {
        return seatsName;
    }

    public void setSeatsName(String seatsName) {
        this.seatsName = seatsName;
    }

    public Double getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(Double filmPrice) {
        this.filmPrice = filmPrice;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(Integer orderUser) {
        this.orderUser = orderUser;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "MoocOrderT{" +
        "uuid=" + uuid +
        ", cinemaId=" + cinemaId +
        ", fieldId=" + fieldId +
        ", filmId=" + filmId +
        ", seatsIds=" + seatsIds +
        ", seatsName=" + seatsName +
        ", filmPrice=" + filmPrice +
        ", orderPrice=" + orderPrice +
        ", orderTime=" + orderTime +
        ", orderUser=" + orderUser +
        ", orderStatus=" + orderStatus +
        "}";
    }
}
