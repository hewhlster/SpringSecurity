package org.fjh.security.dao.impl;

import org.fjh.security.dao.PermissionMapper;
import org.fjh.security.entity.Permission;
import org.fjh.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PermissionMapperImpl implements PermissionMapper {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Permission> queryRolePermission() {
        String sql="select *from tbl_permission";

        List<Permission> permissions=jdbcTemplate.query(sql,
                new RowMapper<Permission>() {
                    @Override
                    public Permission mapRow(ResultSet rs, int i) throws SQLException {
                       Permission p = new Permission();
                       p.setId(rs.getString("id"));
                       p.setPname(rs.getString("pname"));
                       p.setPurl(rs.getString("purl"));
                       p.setPmemo(rs.getString("pmemo"));
                       return p;
                    }
                }
        );

        if( permissions!=null){
            for (Permission p:permissions){
                sql ="SELECT " +
                        "  `tbl_role`.`id`," +
                        "  `tbl_role`.`rname`," +
                        "  `tbl_role`.`rtag`," +
                        "  `tbl_role`.`rmemo` " +
                        "FROM" +
                        "  `springsecurity`.`tbl_role_permission` " +
                        "  INNER JOIN `springsecurity`.`tbl_role` " +
                        "    ON (" +
                        "      `tbl_role_permission`.`rid` = `tbl_role`.`id`" +
                        "    ) " +
                        "    AND tbl_role_permission.`pid` =? ";

                List<Role> roles=jdbcTemplate.query(sql,
                        new Object[]{p.getId()},
                        new RowMapper<Role>() {
                            @Override
                            public Role mapRow(ResultSet rs, int i) throws SQLException {
                                Role r = new Role();
                                r.setId(rs.getString("id"));
                                r.setRname(rs.getString("rname"));
                                r.setRtag(rs.getString("rtag"));
                                r.setRmemo(rs.getString("rmemo"));
                                return r;
                            }
                        });

                p.setRlist(roles);
            }
        }

        return permissions;
    }
}
