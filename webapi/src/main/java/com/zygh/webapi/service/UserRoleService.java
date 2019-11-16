package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.UserRoleMapper;
import pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    public PageInfo findAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<UserRole> list = userRoleMapper.findAll();
        PageInfo<UserRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
