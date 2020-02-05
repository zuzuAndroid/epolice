package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RestrictionWhiteList implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String licenseNumber;
    private Date createDate;
    private String remark;
    private String hpzl;
    private String licenseTypeName;
    private String addUser;
    private String startDate;
    private String endDate;
    private String remark2;
}
