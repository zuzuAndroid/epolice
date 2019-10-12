package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.UserDepartmentMapper;
import com.zygh.webapi.pojo.UserDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDepartmentService {

    @Autowired(required = false)
    private UserDepartmentMapper userDepartmentMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<UserDepartment> list = userDepartmentMapper.findAll();
        PageInfo<UserDepartment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public boolean isExist(String name){
        int exist = userDepartmentMapper.isExist(name);
        if(exist > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public int insert(String name){
        if(name.isEmpty()){
            return -1;
        }
        return userDepartmentMapper.insert(name);
    }

    @Transactional
    public int update(String name,int id){
        if(name.isEmpty()){
            return -1;
        }
        return userDepartmentMapper.updateName(name,id);
    }

    public int remove(int id){
        return userDepartmentMapper.remove(id);
    }
}
