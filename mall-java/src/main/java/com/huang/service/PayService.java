package com.huang.service;

import com.huang.vo.OrderDto;


public interface PayService {

    String pay(OrderDto orderDto) throws Exception;

}
