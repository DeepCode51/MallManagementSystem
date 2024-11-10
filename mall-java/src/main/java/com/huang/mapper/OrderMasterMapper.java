package com.huang.mapper;

import com.huang.entity.OrderMaster;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface OrderMasterMapper extends BaseMapper<OrderMaster> {

    OrderMaster getByOrderId(String orderId);
}
