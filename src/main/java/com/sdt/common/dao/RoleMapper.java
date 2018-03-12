package com.sdt.common.dao;

import java.util.Set;

public interface RoleMapper {
    Set<String> findRoleByUserId(Long userId);
}
