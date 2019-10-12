package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.UserLoginLogMapper;
import com.zygh.webapi.pojo.UserLoginLog;
import com.zygh.webapi.utils.CheckBitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLoginLogService {

    @Autowired(required = false)
    UserLoginLogMapper userLoginLogMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<UserLoginLog> list = userLoginLogMapper.findAll();

        for (UserLoginLog item : list) {
            //String fields = item.getLoginName()+","+item.getLoginIp()+","+item.getLoginDateTime()+","+item.getLoginMsg();
            String fields = item.getFieldsForCheckBit();
            if(CheckBitUtil.check(fields,item.getCheckBit())){
                item.setRedAlert(false);
            }else{
                item.setRedAlert(true);
            }
        }
        PageInfo<UserLoginLog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int insert(UserLoginLog log){
        return userLoginLogMapper.insert(log);
    }
}
