package com.zygh.webapi.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserRegisteRequest {

    private String id;

    private String name;

    private String idCard;

    private String pwd;

    private int role;

    private int status;

    private int department;

    private Date validDate;

    private Date pwdDate;

    private Date createDateTime;

    private String policeId;

    private int limitIp;

    private String startTime;

    private String endTime;
}
