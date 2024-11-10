package com.huang.service;

import com.huang.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地址 服务类
 * </p>
 *
 * @author huang
 * @since 2023-02-08
 */
public interface AddressService extends IService<Address> {

    List<Address> getByUserId(Integer userId);
}
