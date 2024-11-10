package com.huang.service;

import com.huang.entity.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
public interface CommodityService extends IService<Commodity> {

    List<Commodity> getByName(String commodityName);

    List<Commodity> getByType(String commodityType);
}
