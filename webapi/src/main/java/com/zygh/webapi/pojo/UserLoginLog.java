package com.zygh.webapi.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserLoginLog {

    private int id;
    private String loginName;
    private String userAccountId;
    private String userRoleName;
    private String loginIp;
    private Date loginDateTime;
    private String loginMsg;
    private String checkBit;
    private boolean redAlert;

    public String getFieldsForCheckBit(){
        return loginName +"," + loginIp +"," + loginDateTime +"," + loginMsg;
    }
}
