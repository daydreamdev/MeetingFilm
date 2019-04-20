package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 影片与演员映射表
 * </p>
 *
 * @author jiangzh
 * @since 2018-09-08
 */
@TableName("mooc_film_actor_t")
public class MoocFilmActorT extends Model<MoocFilmActorT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 影片编号,对应mooc_film_t
     */
    @TableField("film_id")
    private Integer filmId;
    /**
     * 演员编号,对应mooc_actor_t
     */
    @TableField("actor_id")
    private Integer actorId;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MoocFilmActorT{" +
        "uuid=" + uuid +
        ", filmId=" + filmId +
        ", actorId=" + actorId +
        ", roleName=" + roleName +
        "}";
    }
}
