package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.entity.UserCommodity;
import com.huang.mapper.UserCommodityMapper;
import com.huang.service.UserCommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserCommodityServiceImpl extends ServiceImpl<UserCommodityMapper, UserCommodity> implements UserCommodityService {

    @Override
    public List<UserCommodity> getByUserId(Integer userId) {

        List<UserCommodity> userCommodityList = this.list(new QueryWrapper<UserCommodity>().eq("user_id", userId));

        return userCommodityList;
    }
}
