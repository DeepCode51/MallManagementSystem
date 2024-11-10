package com.huang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.entity.Cart;
import com.huang.entity.Collection;
import com.huang.mapper.CartMapper;
import com.huang.service.CartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Override
    public List<Cart> getByUserId(Integer userId) {

        List<Cart> cartList = this.list(new QueryWrapper<Cart>().eq("user_id", userId));

        return cartList;
    }
}
