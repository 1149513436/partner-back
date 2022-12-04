package com.oyh.partnerback.service;

import com.oyh.partnerback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hrosy
 * @since 2022-12-02
 */
public interface IUserService extends IService<User> {

    User login(User user);

    User register(User user);
}
