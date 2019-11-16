package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class RestrictionWhiteList {

    private int id;
    private String licenseNumber;
    private Date createDate;
    private String remark;
}
