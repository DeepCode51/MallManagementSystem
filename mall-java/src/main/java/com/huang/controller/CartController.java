package com.huang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.common.lang.Result;
import com.huang.entity.Cart;
import com.huang.entity.Commodity;
import com.huang.entity.User;
import com.huang.entity.UserCommodity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api("购物车接口操作")
@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {

    @ApiOperation("查看我的地址")
    @GetMapping("/list")
    public Result list(Principal principal){
        //得到当前登录用户
        User user = userService.getByName(principal.getName());

        //根据当前登录用户的id得到我的软件
        List<UserCommodity> userCommodityList = userCommodityService.getByUserId(user.getUserId());

        List<Commodity> commodities = new ArrayList<Commodity>();

        //遍历我的软件得到软件id,并查出对应软件添加到commodities集合中
        userCommodityList.forEach(commodity -> {
            Integer commodityId = commodity.getCommodityId();
            Commodity goods = commodityService.getById(commodityId);
            commodities.add(goods);
        });

        return Result.succ(commodities);
    }

    @ApiOperation("查看一个购物车接口")
    @GetMapping("/infoo/{commodityId}")
    public Result infoo(@PathVariable("commodityId") Integer commodityId,Principal principal){

        User user = userService.getByName(principal.getName());

        Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("user_id", user.getUserId()).eq("commodity_id", commodityId));

        return Result.succ(cart);
    }

    @ApiOperation("查看一个购物车软件接口")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){

        Cart cart = cartService.getById(id);

        Commodity commodity = commodityService.getById(cart.getCommodityId());

        return Result.succ(commodity);
    }


    @ApiOperation("添加到购物车接口")
    @PostMapping("/save/{commodityId}")
    public Result save(@PathVariable("commodityId") Integer commodityId,Principal principal){

        User user = userService.getByName(principal.getName());

        Cart cart = new Cart();

        cart.setUserId(user.getUserId());
        cart.setCommodityId(commodityId);

        cartService.save(cart);

        return Result.succ("已加入购物车");
    }

    @ApiOperation("删除购物车")
    @PostMapping("/delete/{ids}")
    public Result deleteById(@PathVariable("ids") Integer[] ids){

        cartService.removeByIds(Arrays.asList(ids));

        return Result.succ("删除成功");
    }

}
