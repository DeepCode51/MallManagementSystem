package com.huang.mapper;

import com.huang.entity.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface CommodityMapper extends BaseMapper<Commodity> {

    List<Commodity> getByName(String commodityName);

}
