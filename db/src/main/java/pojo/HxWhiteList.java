package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class HxWhiteList {

    private int id;
    private String licenseNumber;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String addUser;
    private String remark;
}
