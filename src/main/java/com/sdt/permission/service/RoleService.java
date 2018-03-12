package com.sdt.permission.service;

import java.util.Set;

public interface RoleService {
    //根据用户ID查询角色（role），放入到Authorization里。
    Set<String> findRoleByUserId(Long userId);
}
