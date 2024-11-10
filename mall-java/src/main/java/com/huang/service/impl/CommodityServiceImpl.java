package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.entity.Commodity;
import com.huang.mapper.CommodityMapper;
import com.huang.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    CommodityMapper commodityMapper;


    @Override
    public List<Commodity> getByName(String commodityName) {

        List<Commodity> commodityList = commodityMapper.getByName(commodityName);

        return commodityList;
    }

    @Override
    public List<Commodity> getByType(String commodityType) {

        List<Commodity> commodityList = this.list(new QueryWrapper<Commodity>().eq("commodity_type", commodityType));

        return commodityList;
    }
}
