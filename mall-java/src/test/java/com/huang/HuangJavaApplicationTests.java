package com.huang;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.entity.Commodity;
import com.huang.service.CommodityService;
import com.huang.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;


@SpringBootTest
class HuangJavaApplicationTests {

    @Resource
    JavaMailSenderImpl mailSender;

    @Autowired
    UserService userService;

    @Autowired
    CommodityService commodityService;




    @Test
    void contextLoads() {

        Page page = new Page();
        page.setCurrent(1);
        page.setSize(20);

        Page<Commodity> list = commodityService.page(page, new QueryWrapper<Commodity>());

        System.out.println(list.getRecords());

    }


}
