package com.zygh.webapi.service;

import com.zygh.webapi.dao.LoginSetupMapper;
import com.zygh.webapi.pojo.LoginSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginSetupService {

    @Autowired
    LoginSetupMapper loginSetupMapper;

    public LoginSetup find(){
        return loginSetupMapper.find();
    }

    @Transactional
    public int update(LoginSetup setup){
        return loginSetupMapper.update(setup);
    }
}
