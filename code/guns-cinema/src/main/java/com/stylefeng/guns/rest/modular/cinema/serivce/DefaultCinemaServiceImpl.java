package com.stylefeng.guns.rest.modular.cinema.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaServiceAPI.class,executes = 10)
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;
    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;
    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;
    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;
    @Autowired
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;
    @Autowired
    private MoocFieldTMapper moocFieldTMapper;


    //1、根据CinemaQueryVO，查询影院列表
    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO){
        // 业务实体集合
        List<CinemaVO> cinemas = new ArrayList<>();

        Page<MoocCinemaT> page = new Page<>(cinemaQueryVO.getNowPage(),cinemaQueryVO.getPageSize());
        // 判断是否传入查询条件 -> brandId,distId,hallType 是否==99
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        if(cinemaQueryVO.getBrandId() != 99){
            entityWrapper.eq("brand_id",cinemaQueryVO.getBrandId());
        }
        if(cinemaQueryVO.getDistrictId() != 99){
            entityWrapper.eq("area_id",cinemaQueryVO.getDistrictId());
        }
        if(cinemaQueryVO.getHallType() != 99){  // %#3#%
            entityWrapper.like("hall_ids","%#+"+cinemaQueryVO.getHallType()+"+#%");
        }

        // 将数据实体转换为业务实体
        List<MoocCinemaT> moocCinemaTS = moocCinemaTMapper.selectPage(page, entityWrapper);
        for(MoocCinemaT moocCinemaT : moocCinemaTS){
            CinemaVO cinemaVO = new CinemaVO();

            cinemaVO.setUuid(moocCinemaT.getUuid()+"");
            cinemaVO.setMinimumPrice(moocCinemaT.getMinimumPrice()+"");
            cinemaVO.setCinemaName(moocCinemaT.getCinemaName());
            cinemaVO.setAddress(moocCinemaT.getCinemaAddress());

            cinemas.add(cinemaVO);
        }

        // 根据条件，判断影院列表总数
        long counts = moocCinemaTMapper.selectCount(entityWrapper);

        // 组织返回值对象
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemas);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(counts);

        return result;
    }
    //2、根据条件获取品牌列表[除了就99以外，其他的数字为isActive]
    @Override
    public List<BrandVO> getBrands(int brandId){
        boolean flag = false;
        List<BrandVO> brandVOS = new ArrayList<>();
        // 判断brandId是否存在
        MoocBrandDictT moocBrandDictT = moocBrandDictTMapper.selectById(brandId);
        // 判断brandId 是否等于 99
        if(brandId == 99 || moocBrandDictT==null || moocBrandDictT.getUuid() == null){
            flag = true;
        }
        // 查询所有列表
        List<MoocBrandDictT> moocBrandDictTS = moocBrandDictTMapper.selectList(null);
        // 判断flag如果为true，则将99置为isActive
        for(MoocBrandDictT brand : moocBrandDictTS){
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandName(brand.getShowName());
            brandVO.setBrandId(brand.getUuid()+"");
            // 如果flag为true，则需要99，如为false，则匹配上的内容为active
            if(flag){
                if(brand.getUuid() == 99){
                    brandVO.setActive(true);
                }
            }else{
                if(brand.getUuid() == brandId){
                    brandVO.setActive(true);
                }
            }

            brandVOS.add(brandVO);
        }

        return brandVOS;
    }
    //3、获取行政区域列表
    @Override
    public List<AreaVO> getAreas(int areaId){
        boolean flag = false;
        List<AreaVO> areaVOS = new ArrayList<>();
        // 判断brandId是否存在
        MoocAreaDictT moocAreaDictT = moocAreaDictTMapper.selectById(areaId);
        // 判断brandId 是否等于 99
        if(areaId == 99 || moocAreaDictT==null || moocAreaDictT.getUuid() == null){
            flag = true;
        }
        // 查询所有列表
        List<MoocAreaDictT> moocAreaDictTS = moocAreaDictTMapper.selectList(null);
        // 判断flag如果为true，则将99置为isActive
        for(MoocAreaDictT area : moocAreaDictTS){
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaName(area.getShowName());
            areaVO.setAreaId(area.getUuid()+"");
            // 如果flag为true，则需要99，如为false，则匹配上的内容为active
            if(flag){
                if(area.getUuid() == 99){
                    areaVO.setActive(true);
                }
            }else{
                if(area.getUuid() == areaId){
                    areaVO.setActive(true);
                }
            }

            areaVOS.add(areaVO);
        }

        return areaVOS;
    }
    //4、获取影厅类型列表
    @Override
    public List<HallTypeVO> getHallTypes(int hallType){
        boolean flag = false;
        List<HallTypeVO> hallTypeVOS = new ArrayList<>();
        // 判断brandId是否存在
        MoocHallDictT moocHallDictT = moocHallDictTMapper.selectById(hallType);
        // 判断brandId 是否等于 99
        if(hallType == 99 || moocHallDictT==null || moocHallDictT.getUuid() == null){
            flag = true;
        }
        // 查询所有列表
        List<MoocHallDictT> moocHallDictTS = moocHallDictTMapper.selectList(null);
        // 判断flag如果为true，则将99置为isActive
        for(MoocHallDictT hall : moocHallDictTS){
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHalltypeName(hall.getShowName());
            hallTypeVO.setHalltypeId(hall.getUuid()+"");
            // 如果flag为true，则需要99，如为false，则匹配上的内容为active
            if(flag){
                if(hall.getUuid() == 99){
                    hallTypeVO.setActive(true);
                }
            }else{
                if(hall.getUuid() == hallType){
                    hallTypeVO.setActive(true);
                }
            }

            hallTypeVOS.add(hallTypeVO);
        }

        return hallTypeVOS;
    }
    //5、根据影院编号，获取影院信息
    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId){

        // 数据实体
        MoocCinemaT moocCinemaT = moocCinemaTMapper.selectById(cinemaId);
        // 将数据实体转换成业务实体
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        cinemaInfoVO.setCinemaAdress(moocCinemaT.getCinemaAddress());
        cinemaInfoVO.setImgUrl(moocCinemaT.getImgAddress());
        cinemaInfoVO.setCinemaPhone(moocCinemaT.getCinemaPhone());
        cinemaInfoVO.setCinemaName(moocCinemaT.getCinemaName());
        cinemaInfoVO.setCinemaId(moocCinemaT.getUuid()+"");

        return cinemaInfoVO;
    }

    //6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId){

        List<FilmInfoVO> filmInfos = moocFieldTMapper.getFilmInfos(cinemaId);

        return filmInfos;
    }
    //7、根据放映场次ID获取放映信息
    @Override
    public HallInfoVO getFilmFieldInfo(int fieldId){

        HallInfoVO hallInfoVO = moocFieldTMapper.getHallInfo(fieldId);

        return hallInfoVO;
    }
    //8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId){

        FilmInfoVO filmInfoVO = moocFieldTMapper.getFilmInfoById(fieldId);

        return filmInfoVO;
    }

    @Override
    public OrderQueryVO getOrderNeeds(int fieldId) {

        OrderQueryVO orderQueryVO = new OrderQueryVO();

        MoocFieldT moocFieldT = moocFieldTMapper.selectById(fieldId);

        orderQueryVO.setCinemaId(moocFieldT.getCinemaId()+"");
        orderQueryVO.setFilmPrice(moocFieldT.getPrice()+"");

        return orderQueryVO;
    }

}
