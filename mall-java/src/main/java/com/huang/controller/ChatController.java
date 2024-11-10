package com.huang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huang.common.lang.Result;
import com.huang.entity.Chat;
import com.huang.entity.User;
import com.huang.mapper.ChatDao;
import com.huang.service.ChatService;
import com.huang.service.UserService;
import com.huang.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;


/**
 * 在线客服
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
	@Autowired
	private ChatDao chatDao;
	@Autowired
	private UserService userService;
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public Result list(Principal principal){
		User user = userService.getByName(principal.getName());
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("userid",user.getUserId());
		wrapper.orderBy(true,true,"addtime");
//		if(!user.getName().startsWith("admin")) {
//			wrapper.eq("userid",user.getUserId());
//		}
		List<Chat> addressList = chatService.list(wrapper);
		return Result.succ(addressList);
    }

	/**
	 * 后端里列表
	 */
	@RequestMapping("/page")
	public Result page(){
//		User user = userService.getByName(principal.getName());
//		QueryWrapper wrapper = new QueryWrapper();
//		wrapper.eq("userid",user.getUserId());
//		wrapper.orderBy(true,true,"addtime");
//		if(!user.getName().startsWith("admin")) {
//			wrapper.eq("userid",user.getUserId());
//		}
		List<Chat> addressList = chatDao.selectWaitReply();
//		List<Chat> addressList = chatService.list(wrapper);
		return Result.succ(addressList);
	}


	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("userid", userId);
		wrapper.orderBy(true,true,"addtime");
        List<Chat> chat = chatService.list(wrapper);
        return Result.succ(chat);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public Result detail(@PathVariable("id") String id){
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("userid", id);
		wrapper.orderBy(true,true,"addtime");
        List<Chat> chat = chatService.list(wrapper);
        return Result.succ(chat);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Chat chat, Principal principal){
    	chat.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(chat);
		User user = userService.getByName(principal.getName());
		Long userId = Long.valueOf(user.getUserId());
		if(StringUtils.isNotBlank(chat.getAsk())) {
			UpdateWrapper wrapper = new UpdateWrapper();
			wrapper.eq("userid",userId);
			wrapper.setSql("isreply=0");
			chatService.update(wrapper);
    		chat.setUserid(Long.valueOf(user.getUserId()));
    		chat.setIsreply(1);
    	}
    	if(StringUtils.isNotBlank(chat.getReply())) {
			UpdateWrapper wrapper = new UpdateWrapper();
			wrapper.eq("userid",chat.getUserid());
			wrapper.setSql("isreply=0,adminid='"+ userId +"'");
			chatService.update(wrapper);
//    		chatService.updateForSet("isreply=0", new EntityWrapper<ChatEntity>().eq("userid", chat.getUserid()));
    		chat.setAdminid(Long.valueOf(user.getUserId()));
    	}
        chatService.save(chat);
        return Result.succ(chat);
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody Chat chat, Principal principal){
    	chat.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(chat);
		User user = userService.getByName(principal.getName());
		Long userId = Long.valueOf(user.getUserId());
		chat.setUserid(userId);
    	if(StringUtils.isNotBlank(chat.getAsk())) {
			UpdateWrapper wrapper = new UpdateWrapper();
			wrapper.eq("userid",userId);
			wrapper.setSql("isreply=0");
			chatService.update(wrapper);
    		chat.setUserid(userId);
    		chat.setIsreply(1);
    	}
    	if(StringUtils.isNotBlank(chat.getReply())) {
			UpdateWrapper wrapper = new UpdateWrapper();
			wrapper.eq("userid",chat.getUserid());
			wrapper.setSql("isreply=0");
			chatService.update(wrapper);
//    		chatService.updateForSet("isreply=1", new EntityWrapper<ChatEntity>().eq("userid", chat.getUserid()));
    		chat.setAdminid(userId);
    	}
        chatService.save(chat);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Chat chat, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chat);
        chatService.updateById(chat);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String id){
        chatService.removeById(id);
        return R.ok();
    }

}
