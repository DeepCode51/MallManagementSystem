package com.huang.service;

import com.huang.entity.UserCommodity;
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
public interface UserCommodityService extends IService<UserCommodity> {

    List<UserCommodity> getByUserId(Integer userId);

}
