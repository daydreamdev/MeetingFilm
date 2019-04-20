package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 影院信息表
 * </p>
 *
 * @author jiangzh
 * @since 2018-09-15
 */
@TableName("mooc_cinema_t")
public class MoocCinemaT extends Model<MoocCinemaT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 影院名称
     */
    @TableField("cinema_name")
    private String cinemaName;
    /**
     * 影院电话
     */
    @TableField("cinema_phone")
    private String cinemaPhone;
    /**
     * 品牌编号
     */
    @TableField("brand_id")
    private Integer brandId;
    /**
     * 地域编号
     */
    @TableField("area_id")
    private Integer areaId;
    /**
     * 包含的影厅类型,以#作为分割
     */
    @TableField("hall_ids")
    private String hallIds;
    /**
     * 影院图片地址
     */
    @TableField("img_address")
    private String imgAddress;
    /**
     * 影院地址
     */
    @TableField("cinema_address")
    private String cinemaAddress;
    /**
     * 最低票价
     */
    @TableField("minimum_price")
    private Integer minimumPrice;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaPhone() {
        return cinemaPhone;
    }

    public void setCinemaPhone(String cinemaPhone) {
        this.cinemaPhone = cinemaPhone;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getHallIds() {
        return hallIds;
    }

    public void setHallIds(String hallIds) {
        this.hallIds = hallIds;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public Integer getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Integer minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MoocCinemaT{" +
        "uuid=" + uuid +
        ", cinemaName=" + cinemaName +
        ", cinemaPhone=" + cinemaPhone +
        ", brandId=" + brandId +
        ", areaId=" + areaId +
        ", hallIds=" + hallIds +
        ", imgAddress=" + imgAddress +
        ", cinemaAddress=" + cinemaAddress +
        ", minimumPrice=" + minimumPrice +
        "}";
    }
}
