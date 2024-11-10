package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.CommodityChild;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
@Repository
@Mapper
public interface CommodityChildMapper extends BaseMapper<CommodityChild> {

    List<CommodityChild> getByName(String commodityChildName);
}
