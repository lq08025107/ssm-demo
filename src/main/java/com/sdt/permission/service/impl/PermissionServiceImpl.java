package com.sdt.permission.service.impl;

import com.sdt.common.dao.PermissionMapper;
import com.sdt.core.mybatis.BaseMybatisDao;
import com.sdt.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl extends BaseMybatisDao<PermissionMapper> implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    public Set<String> findPermissionByUserId(Long userId) {
        return permissionMapper.findPermissionByUserId(userId);
    }
}
