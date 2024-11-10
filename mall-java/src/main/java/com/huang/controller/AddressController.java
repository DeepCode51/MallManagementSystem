package com.huang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.common.lang.Result;
import com.huang.entity.Address;
import com.huang.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {


    @ApiOperation("查看购物车接口")
    @GetMapping("/list")
    public Result list(Principal principal){
        User user = userService.getByName(principal.getName());
        List<Address> addressList = addressService.getByUserId(user.getUserId());
        return Result.succ(addressList);
    }

    @ApiOperation("查看一个购物车接口")
    @GetMapping("/info/{addressId}")
    public Result infoo(@PathVariable("addressId") Integer addressId,Principal principal){

        User user = userService.getByName(principal.getName());

        Address address = addressService.getOne(new QueryWrapper<Address>().eq("userid", user.getUserId()).eq("id", addressId));

        return Result.succ(address);
    }

    @ApiOperation("新增地址接口")
    @PostMapping("/save")
    public Result save(@RequestBody Address address, Principal principal){
        User user = userService.getByName(principal.getName());
        address.setUserid(user.getUserId());
        addressService.save(address);

        return Result.succ("操作成功！");
    }


    @ApiOperation("删除地址")
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Long[] ids){

        addressService.removeByIds(Arrays.asList(ids));

        return Result.succ("删除成功");
    }

    @ApiOperation("地址修改接口")
    @PostMapping("/update")
    public Result update(@RequestBody Address address, Principal principal){
        addressService.updateById(address);
        return Result.succ("");
    }

}
