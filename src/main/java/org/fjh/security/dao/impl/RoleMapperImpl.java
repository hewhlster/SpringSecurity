package org.fjh.security.dao.impl;

import org.fjh.security.dao.RoleMapper;
import org.fjh.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleMapperImpl implements RoleMapper {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Role> findRolesByUcode(String ucode) {
        List<Role> rlist  = new ArrayList<Role>();
        Role r = new Role();
        r.setRtag("ROLE_ADMIN");
        rlist.add(r);

        Role r1 = new Role();
        r1.setRtag("ROLE_USER");
        rlist.add(r1);

        return rlist;
    }


}
