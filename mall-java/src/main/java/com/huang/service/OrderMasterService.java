package com.huang.service;

import com.huang.entity.OrderMaster;
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
public interface OrderMasterService extends IService<OrderMaster> {

    List<OrderMaster> getByUserId(Integer userId);

    OrderMaster getByOrderId(String orderId);
}
