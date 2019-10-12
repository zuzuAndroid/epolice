package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.UserAccountMapper;
import com.zygh.webapi.pojo.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAccountService {

    @Autowired(required=false)
    private UserAccountMapper userAccountMapper;

    public UserAccount findByName(String username) {
        return userAccountMapper.findByName(username);
    }

    public UserAccount findOne(String username){
        return userAccountMapper.findOne(username);
    }

    public boolean isNameExist(UserAccount user){
        int exist = userAccountMapper.isUserNameExist(user);
        if(exist > 0){
            return true;
        }
        return false;
    }

    public boolean isIdCardExist(UserAccount user){
        int exist = userAccountMapper.isUserIdCardExist(user);
        if(exist > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public int save(UserAccount user){
        return userAccountMapper.save(user);
    }

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<UserAccount> list = userAccountMapper.findAll();
        PageInfo<UserAccount> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public int update(UserAccount user){
        return userAccountMapper.update(user);
    }
}
