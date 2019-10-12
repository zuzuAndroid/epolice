package com.zygh.webapi.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {

    private String idCard;

    private String policeId;

    private int department;

    private int role;

    private String roleName;

    private String deptName;

    private Date validDate;

    private Date pwdDate;

    private String startTime;

    private String endTime;

    private int limitIp;

    private Date updateDateTime;

    private int status;
}
