package com.mmall.common;

/**
 * @author: Guoxy
 * @Email: guoxy@primeton.com
 * @create: 2020-12-07 23:48
 **/
public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }
}
