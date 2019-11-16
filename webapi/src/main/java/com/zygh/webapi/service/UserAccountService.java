package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FirstFilterDto;
import mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.FirstFilterData;
import pojo.UserAccount;
import pojo.UserInfo;

import java.util.List;

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

    public UserAccount findByNickname(String nickname){
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
}
