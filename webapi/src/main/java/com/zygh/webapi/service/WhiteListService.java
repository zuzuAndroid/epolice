package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.pojo.UserDepartment;
import mapper.WhiteListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.WhiteList;

import java.util.List;

@Service
public class WhiteListService {

    @Autowired
    WhiteListMapper whiteListMapper;

    public List<WhiteList> findAll(){
        return whiteListMapper.findAll();
    }

    public PageInfo publicFindAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.PublicFindAll();
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo publicFindByLicenseNumber(String number,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.PublicFindByLicenseNumber(number);
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo privateFindByLicenseNumber(String number,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.privateFindByLicenseNumber(number);
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo privateFindAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.PrivateFindAll();
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(WhiteList params){
        return whiteListMapper.updateById(params);
    }

    public int exists(String number){
        return whiteListMapper.exists(number);
    }

    public int add(WhiteList params){
        return whiteListMapper.add(params);
    }

    public int addToPrivate(WhiteList params){
        return whiteListMapper.addPrivate(params);
    }

    public int checkForValide(String hphm,String dateTime){
        return whiteListMapper.checkForValide(hphm,dateTime);
    }

    public Integer remove(int id){
        return whiteListMapper.remove(id);
    }

    public Integer privateUpdate(WhiteList params){
        return whiteListMapper.privateUpdate(params);
    }
}
