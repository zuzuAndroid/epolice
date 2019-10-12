package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.LoginLockMapper;
import com.zygh.webapi.pojo.LoginLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginLockService {

    @Autowired
    LoginLockMapper loginLockMapper;

    public int insert(LoginLock lock){
        return loginLockMapper.insert(lock);
    }

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<LoginLock> list = loginLockMapper.findAll();
        PageInfo<LoginLock> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
