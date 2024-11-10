package com.huang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.Chat;
import com.huang.mapper.ChatDao;
import com.huang.service.ChatService;
import org.springframework.stereotype.Service;

@Service("chatService")
public class ChatServiceImpl extends ServiceImpl<ChatDao, Chat> implements ChatService {

}
