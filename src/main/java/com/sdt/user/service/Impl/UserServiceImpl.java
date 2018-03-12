package com.sdt.user.service.Impl;

import com.sdt.common.dao.UserMapper;
import com.sdt.common.model.User;
import com.sdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    public User login(String email, String pswd){ return userMapper.login(email, pswd);}

    public int updateByPrimaryKeySelective(User entity){return userMapper.updateByPrimaryKeySelective(entity);}
}
