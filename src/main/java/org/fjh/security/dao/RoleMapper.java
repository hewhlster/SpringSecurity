package org.fjh.security.dao;

import org.fjh.security.entity.Role;

import java.util.List;

public interface RoleMapper {
    public List<Role> findRolesByUcode(String ucode);

}
