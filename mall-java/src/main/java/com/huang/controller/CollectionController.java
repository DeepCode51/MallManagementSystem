package com.huang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.common.lang.Result;
import com.huang.entity.Collection;
import com.huang.entity.Commodity;
import com.huang.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Api("我的收藏接口操作")
@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    @ApiOperation("查看我的收藏接口")
    @GetMapping("/list")
    public Result list(Principal principal){
        //得到当前登录用户
        User user = userService.getByName(principal.getName());

        //根据当前登录用户的id得到我的收藏
        List<Collection> collections = collectionService.getByUserId(user.getUserId());

        List<Commodity> commodities = new ArrayList<Commodity>();

        //遍历我的收藏得到软件id,并查出对应软件添加到commodities集合中
        collections.forEach(collection -> {
            Integer commodityId = collection.getCommodityId();
            Commodity commodity = commodityService.getById(commodityId);
            commodities.add(commodity);
        });

        return Result.succ(commodities);
    }

    @ApiOperation("查看一个收藏接口")
    @GetMapping("/info/{commodityId}")
    public Result info(@PathVariable("commodityId") Integer commodityId,Principal principal){

        User user = userService.getByName(principal.getName());

        Collection collection = collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", user.getUserId()).eq("commodity_id", commodityId));

        return Result.succ(collection);
    }


    @ApiOperation("添加收藏接口")
    @PostMapping("/save/{commodityId}")
    public Result save(@PathVariable(name = "commodityId") Integer commodityId ,Principal principal){

        User user = userService.getByName(principal.getName());

        Integer userId = user.getUserId();

        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setCommodityId(commodityId);

        collectionService.save(collection);

        return Result.succ("收藏成功");
    }


    @ApiOperation("删除收藏")
    @PostMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id){

        collectionService.removeById(id);

        return Result.succ("移除成功");
    }


}
