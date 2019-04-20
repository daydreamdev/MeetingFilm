package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.HallInfoVO;
import lombok.Data;

@Data
public class CinemaFieldResponseVO {

    private CinemaInfoVO cinemaInfo;
    private FilmInfoVO filmInfo;
    private HallInfoVO hallInfo;

}
