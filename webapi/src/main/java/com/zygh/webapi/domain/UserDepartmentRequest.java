package com.zygh.webapi.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDepartmentRequest {

    private int id;

    @ApiModelProperty(notes = "名称")
    private String name;
}
