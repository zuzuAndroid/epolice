package com.zygh.webapi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "登陆参数")
@Data
public class UserLoginRequest {

    @ApiModelProperty(notes = "用户名")
    private String name;

    @ApiModelProperty(notes = "密码")
    private String pwd;
}
