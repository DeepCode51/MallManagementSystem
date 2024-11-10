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
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
@Api("我的已购软件接口操作")
@RestController
@RequestMapping("/userCommodity")
public class UserCommodityController extends BaseController {

    @ApiOperation("查看我的已购软件接口")
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


    @ApiOperation("添加我的已购软件接口")
    @PostMapping("/save/{commodityId}")
    public Result save(@PathVariable(name = "commodityId") Integer commodityId , Principal principal){

        User user = userService.getByName(principal.getName());

        Integer userId = user.getUserId();

        UserCommodity userCommodity = new UserCommodity();
        userCommodity.setUserId(userId);
        userCommodity.setCommodityId(commodityId);

        userCommodityService.save(userCommodity);

        return Result.succ("购买成功");
    }


    @ApiOperation("删除我的软件")
    @PostMapping("/delete/{commodityId}")
    public Result deleteById(@PathVariable("commodityId") Integer commodityId,Principal principal){

        User user = userService.getByName(principal.getName());

        UserCommodity userCommodity = userCommodityService.getOne(new QueryWrapper<UserCommodity>().eq("user_id", user.getUserId()).eq("commodity_id", commodityId));

        userCommodityService.removeById(userCommodity.getId());

        return Result.succ("移除成功");
    }


}
