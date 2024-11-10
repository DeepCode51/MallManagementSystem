
package com.huang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.common.lang.Result;
import com.huang.entity.ConfigEntity;
import com.huang.service.ConfigService;
import com.huang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登录相关
 */
@RequestMapping("config")
@RestController
public class ConfigController extends BaseController{
	
	@Autowired
	private ConfigService configService;

	/**
     * 列表
     */
    @RequestMapping("/page")
    public Result page(@RequestParam Map<String, Object> params, ConfigEntity config){
        Page<ConfigEntity> page = configService.page(getPage(), new QueryWrapper<ConfigEntity>());
        return Result.succ(page);
    }
    
	/**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params,ConfigEntity config){
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank((CharSequence) params.get("name"))) {
            wrapper.like("name", params.get("name"));
        }
        return Result.succ(configService.list(wrapper));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        ConfigEntity config = configService.getById(id);
        return R.ok().put("data", config);
    }
    
    /**
     * 详情
     */
    @RequestMapping("/detail/{id}")
    public Result detail(@PathVariable("id") String id){
        ConfigEntity config = configService.getById(id);
        return Result.succ(config);
    }
    
    /**
     * 根据name获取信息
     */
    @RequestMapping("/info")
    public R infoByName(@RequestParam String name){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        ConfigEntity config = configService.getOne(queryWrapper);
        return R.ok().put("data", config);
    }
    
    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ConfigEntity config){
//    	ValidatorUtils.validateEntity(config);
    	configService.save(config);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ConfigEntity config){
//        ValidatorUtils.validateEntity(config);
        configService.updateById(config);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody ConfigEntity config){
    	configService.removeById(config.getId());
        return R.ok();
    }
}
