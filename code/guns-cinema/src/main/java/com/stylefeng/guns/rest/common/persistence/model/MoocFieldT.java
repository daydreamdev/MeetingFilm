package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 放映场次表
 * </p>
 *
 * @author jiangzh
 * @since 2018-09-15
 */
@TableName("mooc_field_t")
public class MoocFieldT extends Model<MoocFieldT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 影院编号
     */
    @TableField("cinema_id")
    private Integer cinemaId;
    /**
     * 电影编号
     */
    @TableField("film_id")
    private Integer filmId;
    /**
     * 开始时间
     */
    @TableField("begin_time")
    private String beginTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 放映厅类型编号
     */
    @TableField("hall_id")
    private Integer hallId;
    /**
     * 放映厅名称
     */
    @TableField("hall_name")
    private String hallName;
    /**
     * 票价
     */
    private Integer price;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MoocFieldT{" +
        "uuid=" + uuid +
        ", cinemaId=" + cinemaId +
        ", filmId=" + filmId +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", hallId=" + hallId +
        ", hallName=" + hallName +
        ", price=" + price +
        "}";
    }
}
