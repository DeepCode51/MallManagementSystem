package com.huang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.common.lang.Result;
import com.huang.entity.Commodity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;



@Api("商品接口操作")
@RestController
@RequestMapping("/commodity")
public class CommodityController extends BaseController {

    @ApiOperation("获取全部软件信息")
    @GetMapping("/list")
    public Result list(){
        List<Commodity> commodityList = commodityService.list();
        return Result.succ(commodityList);
    }

    /**
     * 获取商品表前20条数据
     * @param: 
     * @return: 
     * @author: huang
     * @date: 2021/12/10 9:15
     */
    @GetMapping("/homeList")
    public Result homeList(){
        Page page = new Page();
        page.setCurrent(1);
        page.setSize(20);

        Page<Commodity> list = commodityService.page(page, new QueryWrapper<Commodity>());
        return Result.succ(list.getRecords());
    }

    /**
     * 分类查询数据
     * @param:
     * @return:
     * @author: huang
     * @date: 2021/12/10 9:16
     */
    @GetMapping("/queryByType/{commodityType}")
    public Result queryByType(@PathVariable("commodityType") String commodityType){
        List<Commodity> commodityList = commodityService.getByType(commodityType);
        return Result.succ(commodityList);
    }

    /**
     * 后台商品分类查询
     * @param: 
     * @return: 
     * @author: huang
     * @date: 2021/12/10 10:51
     */
    @GetMapping("/backQueryByType")
    public Result backQueryByType(){

        String commodityType = ServletRequestUtils.getStringParameter(request, "commodityType","");

        Page<Commodity> page = commodityService.page(getPage(), new QueryWrapper<Commodity>().eq("commodity_type", commodityType));

        return Result.succ(page);
    }


    @ApiOperation("获取分页全部软件信息")
    @GetMapping("/pageList")
    public Result pageList(){

        Page<Commodity> commodityPage = commodityService.page(getPage(), new QueryWrapper<Commodity>());

        return Result.succ(commodityPage);
    }

    @ApiOperation("获取全部软件数量")
    @GetMapping("/sum")
    public Result commoditySum(){

        int count1 = commodityService.count();
        int count2 = commodityChildService.count();

        int total = count1+count2;

        return Result.succ(total);
    }

    @ApiOperation("查看软件详情接口")
    @GetMapping("/info/{id}")
    public Result getInfoById(@PathVariable(name = "id") Long id){

        Commodity commodity = commodityService.getById(id);

        return Result.succ(commodity);
    }

    @ApiOperation("搜索软件接口")
    @GetMapping("/select/{name}")
    public Result select(@PathVariable("name") String name){

        List<Commodity> commodities = commodityService.getByName(name);

        return Result.succ(commodities);
    }


    @ApiOperation("添加软件接口")
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Commodity commodity){

        commodityService.save(commodity);

        return Result.succ("添加成功");
    }


    @ApiOperation("后台修改软件接口")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Commodity commodity){

        commodityService.updateById(commodity);

        return Result.succ("修改成功");
    }


    @ApiOperation("后台下架软件接口")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('admin')")
    public Result deleteById(@RequestBody Integer[] ids){

        commodityService.removeByIds(Arrays.asList(ids));

        return Result.succ("下架成功");
    }


}
