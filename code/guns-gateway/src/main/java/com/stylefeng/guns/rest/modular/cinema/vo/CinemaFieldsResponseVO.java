package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaFieldsResponseVO {

    private CinemaInfoVO cinemaInfo;
    private List<FilmInfoVO> filmList;

}
