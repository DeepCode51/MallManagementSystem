package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.Address;
import com.huang.mapper.AddressMapper;
import com.huang.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地址 服务实现类
 * </p>
 *
 * @author huang
 * @since 2023-02-08
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Override
    public List<Address> getByUserId(Integer userId) {

        List<Address> userAddressList = this.list(new QueryWrapper<Address>().eq("userid", userId));

        return userAddressList;
    }
}
