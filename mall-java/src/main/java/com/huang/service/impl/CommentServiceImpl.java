package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.entity.Comment;
import com.huang.mapper.CommentMapper;
import com.huang.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huang
 * @since 2021-09-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public List<Comment> getByCommodityId(Integer commodityId) {

        List<Comment> commentList = this.list(new QueryWrapper<Comment>().eq("commodity_id", commodityId));

        return commentList;

    }

    @Override
    public List<Comment> getByUserId(Integer userId) {

        List<Comment> commentList = this.list(new QueryWrapper<Comment>().eq("user_id", userId));

        return commentList;
    }
}
