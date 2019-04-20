package com.stylefeng.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.*;

import java.awt.print.Pageable;
import java.util.List;

public interface CinemaServiceAPI {

    //1、根据CinemaQueryVO，查询影院列表
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);
    //2、根据条件获取品牌列表[除了就99以外，其他的数字为isActive]
    List<BrandVO> getBrands(int brandId);
    //3、获取行政区域列表
    List<AreaVO> getAreas(int areaId);
    //4、获取影厅类型列表
    List<HallTypeVO> getHallTypes(int hallType);
    //5、根据影院编号，获取影院信息
    CinemaInfoVO getCinemaInfoById(int cinemaId);
    //6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId);
    //7、根据放映场次ID获取放映信息
    HallInfoVO getFilmFieldInfo(int fieldId);
    //8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    FilmInfoVO getFilmInfoByFieldId(int fieldId);

    /*
        该部分是订单模块需要的内容
     */
    OrderQueryVO getOrderNeeds(int fieldId);

}
