package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.RoleMapper;
import mapper.UserRoleMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import pojo.CarBrand;
import pojo.Role;
import pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    RoleMapper roleMapper;


    @Cacheable(value="userRole", key="'userRole'")
    public List<UserRole> findAllNoPage(){
        return roleMapper.findAll();
    }

    public PageInfo findAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<UserRole> list = roleMapper.findAll();
        PageInfo<UserRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @CacheEvict(value="userRole",allEntries=true)
    public int update(Role params){
        return roleMapper.update(params);
    }

    @CacheEvict(value="userRole",allEntries=true)
    public int add(Role params){
        return roleMapper.add(params);
    }

    @CacheEvict(value="userRole",allEntries=true)
    public int remove(int id){
        return roleMapper.remove(id);
    }
}
