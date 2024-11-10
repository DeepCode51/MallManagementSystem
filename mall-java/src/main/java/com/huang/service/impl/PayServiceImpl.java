package com.huang.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.huang.service.PayService;
import com.alipay.easysdk.factory.Factory;
import com.huang.utils.OrderUtil;
import com.huang.vo.OrderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

@Service
public class PayServiceImpl implements PayService {

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Override
    public String pay(OrderDto orderDto) throws Exception {
        AlipayTradePagePayResponse response = Factory.Payment
                .Page()
                .pay(orderDto.getName(), OrderUtil.getOrderNo(), orderDto.getPrice().toString(), returnUrl);

        String res = JSONUtil.toJsonStr(response.getBody());

        return res;
    }
}
