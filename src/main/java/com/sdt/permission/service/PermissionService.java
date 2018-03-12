package com.sdt.permission.service;

import java.util.Set;

public interface PermissionService {

    Set<String> findPermissionByUserId(Long userId);
}
