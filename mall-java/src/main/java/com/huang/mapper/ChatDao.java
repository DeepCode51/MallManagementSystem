package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.huang.entity.Chat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 在线客服
 * 
 * @author 
 * @email 
 * @date 2021-03-13 12:49:51
 */
public interface ChatDao extends BaseMapper<Chat> {

	@Select("SELECT * FROM (\n" +
			"SELECT T.USERID,T.addtime,T.ask,ROW_NUMBER()OVER(PARTITION BY USERID ORDER BY ADDTIME DESC) RN\n" +
			"FROM `chat` T\n" +
			"WHERE ADMINID IS NULL and isreply=1)E WHERE RN=1\n" +
			"ORDER BY ADDTIME DESC")
	List<Chat> selectWaitReply();

	
	List<Chat> selectListVO(@Param("ew") Wrapper<Chat> wrapper);

	Chat selectVO(@Param("ew") Wrapper<Chat> wrapper);
	
	List<Chat> selectListView(@Param("ew") Wrapper<Chat> wrapper);

	List<Chat> selectListView(Pagination page, @Param("ew") Wrapper<Chat> wrapper);

	Chat selectView(@Param("ew") Wrapper<Chat> wrapper);
	
}
