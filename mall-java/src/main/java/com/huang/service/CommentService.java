package com.huang.service;

import com.huang.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huang
 * @since 2021-09-26
 */
public interface CommentService extends IService<Comment> {

    List<Comment> getByCommodityId(Integer commodityId);

    List<Comment> getByUserId(Integer userId);
}
