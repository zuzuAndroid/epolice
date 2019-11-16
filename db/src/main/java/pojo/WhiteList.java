package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class WhiteList {

    private int id;
    private String licenseNumber;
    private String licenseType;
    private String licenseTypeName;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private int userRight;
    private int displayRight;
    private String addUser;
    private String remark;
    private int illegalType;
}
