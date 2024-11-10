package com.huang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.common.lang.Result;
import com.huang.entity.CommodityChild;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



@Api("子软件接口操作")
@RestController
@RequestMapping("/commodityChild")
public class CommodityChildController extends BaseController {


    @ApiOperation("获取全部子软件信息")
    @GetMapping("/list")
    public Result list(){
        List<CommodityChild> commodityChildList = commodityChildService.list();
        return Result.succ(commodityChildList);
    }

    @ApiOperation("获取分页全部软件信息")
    @GetMapping("/pageList")
    public Result pageList(){

        Page<CommodityChild> commodityChildPage = commodityChildService.page(getPage(), new QueryWrapper<CommodityChild>());

        return Result.succ(commodityChildPage);
    }

    @ApiOperation("查看软件详情接口")
    @GetMapping("/info/{id}")
    public Result getInfoById(@PathVariable(name = "id") Long id){

        CommodityChild commodityChild = commodityChildService.getById(id);

        return Result.succ(commodityChild);
    }

    @ApiOperation("搜索软件详情接口")
    @GetMapping("/select/{commodityChildName}")
    public Result getInfoByName(@PathVariable("commodityChildName") String commodityChildName){

        List<CommodityChild> commodityChildList = commodityChildService.getByName(commodityChildName);

        return Result.succ(commodityChildList);
    }


    @ApiOperation("添加软件接口")
    @PostMapping("/save")
    //@PreAuthorize("hasAuthority('1')")
    public Result save(@Validated @RequestBody CommodityChild commodityChild){

        commodityChildService.save(commodityChild);

        return Result.succ("添加成功");
    }

    @ApiOperation("后台修改软件接口")
    @PostMapping("/update")
    @PreAuthorize("hasRole('admin')")
    public Result update(@Validated @RequestBody CommodityChild commodityChild){

        commodityChildService.updateById(commodityChild);

        return Result.succ("修改成功");
    }


    @ApiOperation("后台下架软件接口")
    @PostMapping("/delete/{ids}")
    @PreAuthorize("hasRole('admin')")
    public Result deleteById(@PathVariable("ids") Integer[] ids){

        commodityChildService.removeByIds(Arrays.asList(ids));

        return Result.succ("下架成功");
    }



}
