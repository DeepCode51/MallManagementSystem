package com.huang.service;

import com.huang.entity.Collection;
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
public interface CollectionService extends IService<Collection> {

    List<Collection> getByUserId(Integer userId);
}
