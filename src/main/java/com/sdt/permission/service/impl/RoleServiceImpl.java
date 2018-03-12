package com.sdt.permission.service.impl;

import com.sdt.common.dao.RoleMapper;
import com.sdt.core.mybatis.BaseMybatisDao;
import com.sdt.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl extends BaseMybatisDao<RoleMapper> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    public Set<String> findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
