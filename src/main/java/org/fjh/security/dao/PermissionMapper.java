package org.fjh.security.dao;

import org.fjh.security.entity.Permission;
import org.fjh.security.entity.Role;

import java.util.List;

public interface PermissionMapper {
    public List<Permission> queryRolePermission();

}
