package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * banner信息表
 * </p>
 *
 * @author jiangzh
 * @since 2018-08-26
 */
@TableName("mooc_banner_t")
public class MoocBannerT extends Model<MoocBannerT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * banner图存放路径
     */
    @TableField("banner_address")
    private String bannerAddress;
    /**
     * banner点击跳转url
     */
    @TableField("banner_url")
    private String bannerUrl;
    /**
     * 是否弃用 0-失效,1-失效
     */
    @TableField("is_valid")
    private Integer isValid;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getBannerAddress() {
        return bannerAddress;
    }

    public void setBannerAddress(String bannerAddress) {
        this.bannerAddress = bannerAddress;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MoocBannerT{" +
        "uuid=" + uuid +
        ", bannerAddress=" + bannerAddress +
        ", bannerUrl=" + bannerUrl +
        ", isValid=" + isValid +
        "}";
    }
}
