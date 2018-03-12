package com.sdt.common.dao;

import java.util.Set;

public interface PermissionMapper {
    Set<String> findPermissionByUserId(Long userId);
}
