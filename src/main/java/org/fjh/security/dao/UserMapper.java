package org.fjh.security.dao;

import org.fjh.security.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    public User findByUcode(String code);

    public List findPermissionByUcode(String code);

    public List findRolesByUcode(String code);
}
