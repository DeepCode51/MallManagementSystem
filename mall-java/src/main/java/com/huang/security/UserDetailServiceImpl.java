package com.huang.security;


import com.huang.entity.User;
import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.getByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser(user.getUserId(), user.getName(), user.getPass(),getUserAuthority(user.getUserId()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(Integer userId){

		// 角色(ROLE_admin)、添加软件操作权限 "1"
		String authority = userService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,1

		//AuthorityUtils工具:封装成List<GrantedAuthority>类型
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
	}
}
