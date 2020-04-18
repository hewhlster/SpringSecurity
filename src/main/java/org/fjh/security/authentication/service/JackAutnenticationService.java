package org.fjh.security.authentication.service;

import org.fjh.security.authentication.bean.AuthenticationUser;
import org.fjh.security.dao.UserMapper;
import org.fjh.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JackAutnenticationService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticatPermission authenticatPermission;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查找用户
        User retUser= userMapper.findByUcode(username);
        if( retUser==null)
            return  null;
        else
            //返回认证的用户
            return createAuthenticationUser(retUser);
    }

    private UserDetails createAuthenticationUser(User user){
        return  new
                AuthenticationUser(
                        user.getId(),
                        user.getUcode(),
                        user.getUsername(),
                        user.getPassword(),
                        authenticatPermission.mapToGrantedAuthorities(user)
                );
    }
}
