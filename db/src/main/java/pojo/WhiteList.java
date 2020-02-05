package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WhiteList implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String licenseNumber;
    private String licenseType;
    private String licenseTypeName;
    private Date createDate;

    private String startDate;
    private String endDate;

    private int userRight;
    private int displayRight;
    private String addUser;
    private String remark;
    private String illegalType;
}
