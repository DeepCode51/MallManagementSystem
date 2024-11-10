package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.entity.OrderMaster;
import com.huang.mapper.OrderMasterMapper;
import com.huang.service.OrderMasterService;
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
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements OrderMasterService {

    @Autowired
    OrderMasterMapper orderMasterMapper;

    @Override
    public List<OrderMaster> getByUserId(Integer userId) {
        List<OrderMaster> masterList = this.list(new QueryWrapper<OrderMaster>().eq("user_id", userId));
        return masterList;
    }

    @Override
    public OrderMaster getByOrderId(String orderId) {
        OrderMaster master = orderMasterMapper.getByOrderId(orderId);
        return master;
    }
}
