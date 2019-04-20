package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 影片主表
 * </p>
 *
 * @author jiangzh
 * @since 2018-08-26
 */
@TableName("mooc_film_t")
public class MoocFilmT extends Model<MoocFilmT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 影片名称
     */
    @TableField("film_name")
    private String filmName;
    /**
     * 片源类型: 0-2D,1-3D,2-3DIMAX,4-无
     */
    @TableField("film_type")
    private Integer filmType;
    /**
     * 影片主图地址
     */
    @TableField("img_address")
    private String imgAddress;
    /**
     * 影片评分
     */
    @TableField("film_score")
    private String filmScore;
    /**
     * 影片预售数量
     */
    @TableField("film_preSaleNum")
    private Integer filmPresalenum;
    /**
     * 影片票房：每日更新，以万为单位
     */
    @TableField("film_box_office")
    private Integer filmBoxOffice;
    /**
     * 影片片源，参照片源字典表
     */
    @TableField("film_source")
    private String filmSource;
    /**
     * 影片分类，参照分类表,多个分类以,分割
     */
    @TableField("film_cats")
    private String filmCats;
    /**
     * 影片区域，参照区域表
     */
    @TableField("film_area")
    private Integer filmArea;
    /**
     * 影片上映年代，参照年代表
     */
    @TableField("film_date")
    private Integer filmDate;
    /**
     * 影片上映时间
     */
    @TableField("film_time")
    private Date filmTime;
    /**
     * 影片状态,1-正在热映，2-即将上映，3-经典影片
     */
    @TableField("film_status")
    private Integer filmStatus;


    public String getFilmSource() {
        return filmSource;
    }

    public void setFilmSource(String filmSource) {
        this.filmSource = filmSource;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Integer getFilmType() {
        return filmType;
    }

    public void setFilmType(Integer filmType) {
        this.filmType = filmType;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getFilmScore() {
        return filmScore;
    }

    public void setFilmScore(String filmScore) {
        this.filmScore = filmScore;
    }

    public Integer getFilmPresalenum() {
        return filmPresalenum;
    }

    public void setFilmPresalenum(Integer filmPresalenum) {
        this.filmPresalenum = filmPresalenum;
    }

    public Integer getFilmBoxOffice() {
        return filmBoxOffice;
    }

    public void setFilmBoxOffice(Integer filmBoxOffice) {
        this.filmBoxOffice = filmBoxOffice;
    }

    public String getFilmCats() {
        return filmCats;
    }

    public void setFilmCats(String filmCats) {
        this.filmCats = filmCats;
    }

    public Integer getFilmArea() {
        return filmArea;
    }

    public void setFilmArea(Integer filmArea) {
        this.filmArea = filmArea;
    }

    public Integer getFilmDate() {
        return filmDate;
    }

    public void setFilmDate(Integer filmDate) {
        this.filmDate = filmDate;
    }

    public Date getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(Date filmTime) {
        this.filmTime = filmTime;
    }

    public Integer getFilmStatus() {
        return filmStatus;
    }

    public void setFilmStatus(Integer filmStatus) {
        this.filmStatus = filmStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MoocFilmT{" +
        "uuid=" + uuid +
        ", filmName=" + filmName +
        ", filmType=" + filmType +
        ", imgAddress=" + imgAddress +
        ", filmScore=" + filmScore +
        ", filmPresalenum=" + filmPresalenum +
        ", filmBoxOffice=" + filmBoxOffice +
        ", filmCats=" + filmCats +
        ", filmArea=" + filmArea +
        ", filmDate=" + filmDate +
        ", filmTime=" + filmTime +
        ", filmStatus=" + filmStatus +
        "}";
    }
}
