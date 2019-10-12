package com.zygh.webapi.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder(alphabetic = true)
public class UserDepartment {

    @JsonInclude
    private int id;

    @JsonInclude
    private String name;
}
