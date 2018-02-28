package com.sdt.common.dao;

import com.sdt.common.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);
    int insert(User record);
}
