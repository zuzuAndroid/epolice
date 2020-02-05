package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HxWhiteList implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String licenseNumber;
    private String licenseTypeName;
    private String hpzl;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String addUser;
    private String remark;
}
