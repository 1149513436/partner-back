package com.oyh.partnerback.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oyh.partnerback.common.Constants;
import com.oyh.partnerback.entity.User;
import com.oyh.partnerback.exception.ServiceException;
import com.oyh.partnerback.mapper.UserMapper;
import com.oyh.partnerback.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hrosy
 * @since 2022-12-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(User user) {
        User dbUser;
        try {
            dbUser= getOne(new UpdateWrapper<User>().eq("username",user.getUsername()));

        }catch (Exception e){
            throw new RuntimeException("系统异常");
        }
        if(dbUser==null){
            throw new ServiceException("未找到用户");
        }else{
            if(!user.getPassword().equals(dbUser.getPassword())){
                throw new ServiceException("用户名或密码错误");
            }
        }
        return dbUser;
    }

    @Override
    public User register(User user) {
        try{
            User dbUser= getOne(new UpdateWrapper<User>().eq("username",user.getUsername()));
            if(dbUser!=null){
                throw new ServiceException("用户已存在");
            }
//            如果注册时没有传入密码设置默认密码
            if(StrUtil.isBlank(user.getPassword())){
                user.setPassword("123456");
            }
//            设置昵称
            if(StrUtil.isBlank(user.getName())){
                user.setName(Constants.USER_NAME_PREFIX+ DateUtil.format(new Date(),Constants.DATE_RULE_YYYYMMDD)+ RandomUtil.randomString(4));
            }
            boolean saveSuccess= save(user);
            if(!saveSuccess){
                throw new RuntimeException("注册失败");
            }
            return user;
        }catch (Exception e){
            throw new RuntimeException("数据库异常");
        }
    }
}
