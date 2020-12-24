package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @author: Guoxy
 * @Email: guoxy@primeton.com
 * @create: 2020-12-07 22:43
 **/
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str,String type);

    public ServerResponse selectQuestion(String username);
}
