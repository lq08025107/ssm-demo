package com.sdt.user.service;

import com.sdt.common.model.User;

public interface UserService {
    User findUserByEmail(String email);

    //登陆
    User login(String email, String pswd);

    int updateByPrimaryKeySelective(User entity);
}
