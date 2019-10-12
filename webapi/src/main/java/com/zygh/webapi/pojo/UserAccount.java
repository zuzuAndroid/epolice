package com.zygh.webapi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Date;

@Data
@JsonPropertyOrder(alphabetic = true)
public class UserAccount {

    @JsonInclude
    private String id;

    @JsonInclude
    private String name;

    @JsonIgnore
    private String pwd;

    @JsonInclude
    private String idCard;

    @JsonInclude
    private int role;

    @JsonInclude
    private String roleName;

    @JsonInclude
    private int status;

    @JsonInclude
    private int department;

    @JsonInclude
    private String deptName;

    @JsonInclude
    private Date validDate;

    @JsonInclude
    private Date pwdDate;

    @JsonInclude
    private Date createDateTime;

    @JsonInclude
    private Date updateDateTime;

    @JsonInclude
    private String policeId;

    @JsonInclude
    private int limitIp;

    @JsonInclude
    private String startTime;

    @JsonInclude
    private String endTime;
}
