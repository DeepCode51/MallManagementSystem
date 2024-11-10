
package com.huang.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.ConfigEntity;
import com.huang.mapper.ConfigDao;
import com.huang.service.ConfigService;
import org.springframework.stereotype.Service;


/**
 * 系统用户
 */
@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigEntity> implements ConfigService {

}
