package org.fjh.security.authentication.service;

import org.fjh.security.dao.RoleMapper;
import org.fjh.security.dao.UserMapper;
import org.fjh.security.entity.Role;
import org.fjh.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthenticatPermission {
    @Autowired
    UserMapper userMapper;

    //根据用户ucode得到其对应的角色
    public Collection<GrantedAuthority> mapToGrantedAuthorities(User user) {

        List<Role> rlist=  userMapper.findRolesByUcode(user.getUcode());
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role role:rlist){
            SimpleGrantedAuthority simpleGrantedAuthority  = new SimpleGrantedAuthority(role.getRtag());
            ((ArrayList<GrantedAuthority>) grantedAuthorities).add(simpleGrantedAuthority);
        }
        return grantedAuthorities;
    }
}
