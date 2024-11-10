package com.huang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.common.lang.Result;
import com.huang.entity.Commodity;
import com.huang.entity.OrderMaster;
import com.huang.entity.User;
import com.huang.service.PayService;
import com.huang.utils.OrderUtil;
import com.huang.vo.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
@Api("订单接口操作")
@RestController
@RequestMapping("/orderMaster")
public class OrderMasterController extends BaseController {

    @Autowired
    PayService payService;

    @ApiOperation("后台获取全部订单接口")
    @GetMapping("/allOrder")
    public Result allOrder(){
        ArrayList<OrderDto> allOrderList = new ArrayList<>();
        List<OrderMaster> list = orderMasterService.list();
        list.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            Commodity commodity = commodityService.getById(order.getCommodityId());

            orderDto.setId(order.getId());
            orderDto.setOrderId(order.getOrderId());
            orderDto.setName(commodity.getName());
            orderDto.setShop(commodity.getShop());
            orderDto.setStatus(order.getStatus());
            orderDto.setPrice(commodity.getPrice());

            allOrderList.add(orderDto);
        });

        return Result.succ(allOrderList);
    }

    @ApiOperation("后台通过订单号搜索订单接口")
    @GetMapping("/select/{orderId}")
    public Result select(@PathVariable("orderId") String orderId){

        ArrayList<OrderDto> arrayList = new ArrayList<>();

        OrderDto orderDto = new OrderDto();

        OrderMaster master = orderMasterService.getByOrderId(orderId);

        Commodity commodity = commodityService.getById(master.getCommodityId());

        orderDto.setId(master.getId());
        orderDto.setOrderId(master.getOrderId());
        orderDto.setName(commodity.getName());
        orderDto.setShop(commodity.getShop());
        orderDto.setStatus(master.getStatus());
        orderDto.setPrice(commodity.getPrice());

        arrayList.add(orderDto);

        return Result.succ(arrayList);

    }


    @ApiOperation("获取全部订单接口")
    @GetMapping("/list")
    public Result list(Principal principal){

        User user = userService.getByName(principal.getName());

        List<OrderDto> orderDtoList = new ArrayList<OrderDto>();

        List<OrderMaster> orderMasterList = orderMasterService.getByUserId(user.getUserId());

        //List<OrderMaster> orderMasterList = orderMasterService.list();

        orderMasterList.forEach(orderMaster -> {
            OrderDto orderDto = new OrderDto();
            Integer commodityId = orderMaster.getCommodityId();
            //Integer userId = orderMaster.getUserId();
            //User user = userService.getOne(new QueryWrapper<User>().eq("user_id", userId));
            Commodity commodity = commodityService.getById(commodityId);

            orderDto.setId(orderMaster.getId());
            orderDto.setOrderId(orderMaster.getOrderId());
            orderDto.setName(commodity.getName());
            orderDto.setShop(commodity.getShop());
            orderDto.setStatus(orderMaster.getStatus());
            orderDto.setPrice(commodity.getPrice());
            orderDto.setEmail(user.getEmail());
            orderDto.setCommodityId(commodity.getId());

            orderDtoList.add(orderDto);
        });


        return Result.succ(orderDtoList);
    }

    @ApiOperation("获取订单总数接口")
    @GetMapping("/sum")
    public Result countBySum(){

        int count = orderMasterService.count();

        return Result.succ(count);
    }

    @ApiOperation("获取一个订单接口")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        OrderDto orderDto = new OrderDto();

        OrderMaster orderMaster = orderMasterService.getById(id);

        Commodity commodity = commodityService.getById(orderMaster.getCommodityId());
        User user = userService.getOne(new QueryWrapper<User>().eq("user_id", orderMaster.getUserId()));

        orderDto.setId(orderMaster.getId());
        orderDto.setOrderId(orderMaster.getOrderId());
        orderDto.setName(commodity.getName());
        orderDto.setShop(commodity.getShop());
        orderDto.setStatus(orderMaster.getStatus());
        orderDto.setPrice(commodity.getPrice());
        orderDto.setEmail(user.getEmail());
        orderDto.setCommodityId(commodity.getId());

        return Result.succ(orderDto);
    }



    @ApiOperation("生成订单接口")
    @PostMapping("/save/{commodityId}")
    public Result save(@PathVariable("commodityId") Integer commodityId , Principal principal){

        User user = userService.getByName(principal.getName());

        OrderMaster orderMaster = new OrderMaster();

        //未支付状态
        orderMaster.setStatus(0);

        //根据时间戳生成订单号
        orderMaster.setOrderId(OrderUtil.getOrderNo());

        orderMaster.setCommodityId(commodityId);
        orderMaster.setUserId(user.getUserId());

        orderMasterService.save(orderMaster);

        return Result.succ(orderMaster);
    }

    @ApiOperation("支付宝支付接口")
    @PostMapping("/alipay/{commodityId}")
    public Result alipay(@PathVariable("commodityId") Integer commodityId) throws Exception {

        Commodity commodity = commodityService.getById(commodityId);

        OrderDto orderDto = new OrderDto();

        orderDto.setName(commodity.getName());
        orderDto.setPrice(commodity.getPrice());

        String pay = payService.pay(orderDto);

        return Result.succ(pay);
    }


    @ApiOperation("结算订单接口")
    @PostMapping("/paidSave/{id}")
    public Result paidSave(@PathVariable("id") Integer id){

        OrderMaster orderMaster = orderMasterService.getById(id);
        orderMaster.setStatus(1);

        orderMasterService.updateById(orderMaster);
        return Result.succ("");
    }


    @ApiOperation("删除订单")
    @PostMapping("/delete")
    public Result deleteByIds(@RequestBody Integer[] ids){
        //@RequestBody：前端this.$axios.post("/delete", ids).then(res => 这样推送参数
        orderMasterService.removeByIds(Arrays.asList(ids));

        return Result.succ("移除成功");
    }

}
