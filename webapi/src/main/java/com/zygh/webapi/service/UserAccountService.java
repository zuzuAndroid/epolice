package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FirstFilterDto;
import dto.UserPasswordDto;
import mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.FirstFilterData;
import pojo.UserAccount;
import pojo.UserInfo;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class UserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<UserInfo> list = userAccountMapper.findAll();

        PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Cacheable(value="userAccount", key="#nickname")
    public UserAccount findByNickname(String nickname){
        return userAccountMapper.findByNickname(nickname);
    }

    public UserAccount checkNickname(String nickname){
        return userAccountMapper.findByNickname(nickname);
    }

    public UserInfo getUserInfo(String nickname){
        return userAccountMapper.getUserByNickname(nickname);
    }

    @Transactional
    public int addUser(UserAccount user){
        return userAccountMapper.addUser(user);
    }

    public int updateUser(UserAccount user){
        return userAccountMapper.updateUser(user);
    }

    public int updateUserPassword(UserPasswordDto params){
        return userAccountMapper.updateUserPwd(params);
    }

    public String findPwdById(int id){
        return userAccountMapper.findPwdById(id);
    }

    public Integer remove(int id){
        return userAccountMapper.remove(id);
    }

    @CacheEvict(value="userAccount",allEntries=true)
    public void logout(){

    }
}
