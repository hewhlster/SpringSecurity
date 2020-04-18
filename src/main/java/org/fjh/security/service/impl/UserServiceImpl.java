package org.fjh.security.service.impl;

import org.fjh.security.dao.RoleMapper;
import org.fjh.security.dao.UserMapper;
import org.fjh.security.entity.Role;
import org.fjh.security.entity.User;
import org.fjh.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public User getByUcode(String ucode) {
        User retUser = userMapper.findByUcode("");

        List<Role> roles=roleMapper.findRolesByUcode("");

        retUser.setRlist(roles);

        return retUser;
    }
}
