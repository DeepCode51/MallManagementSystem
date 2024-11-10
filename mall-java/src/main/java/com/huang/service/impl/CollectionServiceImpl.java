package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.entity.Collection;
import com.huang.mapper.CollectionMapper;
import com.huang.service.CollectionService;
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
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {


    @Override
    public List<Collection> getByUserId(Integer userId) {
        List<Collection> collections = this.list(new QueryWrapper<Collection>().eq("user_id", userId));

        return collections;
    }
}
