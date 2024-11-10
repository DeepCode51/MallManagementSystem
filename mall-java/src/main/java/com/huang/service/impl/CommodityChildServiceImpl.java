package com.huang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.CommodityChild;
import com.huang.mapper.CommodityChildMapper;
import com.huang.service.CommodityChildService;
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
public class CommodityChildServiceImpl extends ServiceImpl<CommodityChildMapper, CommodityChild> implements CommodityChildService {

    @Autowired
    CommodityChildMapper commodityChildMapper;

    @Override
    public List<CommodityChild> getByName(String commodityChildName) {

        List<CommodityChild> commodityChildList = commodityChildMapper.getByName(commodityChildName);

        return commodityChildList;
    }
}
