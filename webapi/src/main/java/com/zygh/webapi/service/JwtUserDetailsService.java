package com.zygh.webapi.service;


import com.zygh.webapi.dao.UserRoleMapper;
import com.zygh.webapi.pojo.UserAccount;
import com.zygh.webapi.pojo.UserRole;
import com.zygh.webapi.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired(required=false)
    private UserRoleMapper userRoleMapper;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {

        UserAccount userAccount = userAccountService.findByName(value);

        if (userAccount == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", value));
        }

        List<UserRole> roles = this.userRoleMapper.findAll();

        return new CustomUserDetails(userAccount, roles);
    }
}