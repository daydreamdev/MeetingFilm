package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-09-06
 */
@TableName("mooc_film_info_t")
public class MoocFilmInfoT extends Model<MoocFilmInfoT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 影片编号
     */
    @TableField("film_id")
    private String filmId;
    /**
     * 影片英文名称
     */
    @TableField("film_en_name")
    private String filmEnName;
    /**
     * 影片评分
     */
    @TableField("film_score")
    private String filmScore;
    /**
     * 评分人数,以万为单位
     */
    @TableField("film_score_num")
    private Integer filmScoreNum;
    /**
     * 播放时长，以分钟为单位，不足取整
     */
    @TableField("film_length")
    private Integer filmLength;
    /**
     * 影片介绍
     */
    private String biography;
    /**
     * 导演编号
     */
    @TableField("director_id")
    private Integer directorId;
    /**
     * 影片图片集地址,多个图片以逗号分隔
     */
    @TableField("film_imgs")
    private String filmImgs;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmEnName() {
        return filmEnName;
    }

    public void setFilmEnName(String filmEnName) {
        this.filmEnName = filmEnName;
    }

    public String getFilmScore() {
        return filmScore;
    }

    public void setFilmScore(String filmScore) {
        this.filmScore = filmScore;
    }

    public Integer getFilmScoreNum() {
        return filmScoreNum;
    }

    public void setFilmScoreNum(Integer filmScoreNum) {
        this.filmScoreNum = filmScoreNum;
    }

    public Integer getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(Integer filmLength) {
        this.filmLength = filmLength;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getFilmImgs() {
        return filmImgs;
    }

    public void setFilmImgs(String filmImgs) {
        this.filmImgs = filmImgs;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MoocFilmInfoT{" +
        "uuid=" + uuid +
        ", filmId=" + filmId +
        ", filmEnName=" + filmEnName +
        ", filmScore=" + filmScore +
        ", filmScoreNum=" + filmScoreNum +
        ", filmLength=" + filmLength +
        ", biography=" + biography +
        ", directorId=" + directorId +
        ", filmImgs=" + filmImgs +
        "}";
    }
}
