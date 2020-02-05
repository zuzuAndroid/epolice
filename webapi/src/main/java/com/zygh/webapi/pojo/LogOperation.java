package com.zygh.webapi.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LogOperation {

    private String id;
    private String userName;
    private String userIp;
    private int operType;
    private String operEvent;
    private String operUrl;
    private Date operDateTime;
}
