package com.huang.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 在线客服
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2021-03-13 12:49:51
 */
@TableName("chat")
public class Chat<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public Chat() {
		
	}

	
	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
					
	private Long userid;
	
	/**
	 * 管理员id
	 */
					
	private Long adminid;
	
	/**
	 * 提问
	 */
					
	private String ask;
	
	/**
	 * 回复
	 */
					
	private String reply;
	
	/**
	 * 是否回复
	 */
					
	private Integer isreply;
	
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 设置：管理员id
	 */
	public void setAdminid(Long adminid) {
		this.adminid = adminid;
	}
	/**
	 * 获取：管理员id
	 */
	public Long getAdminid() {
		return adminid;
	}
	/**
	 * 设置：提问
	 */
	public void setAsk(String ask) {
		this.ask = ask;
	}
	/**
	 * 获取：提问
	 */
	public String getAsk() {
		return ask;
	}
	/**
	 * 设置：回复
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}
	/**
	 * 获取：回复
	 */
	public String getReply() {
		return reply;
	}
	/**
	 * 设置：是否回复
	 */
	public void setIsreply(Integer isreply) {
		this.isreply = isreply;
	}
	/**
	 * 获取：是否回复
	 */
	public Integer getIsreply() {
		return isreply;
	}

}
