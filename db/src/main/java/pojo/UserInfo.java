package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String name;
    private String nickname;
    private int deptId;
    private int roleId;
    private String roleName;
    private String deptName;
    private String permissions;
    private int status;
}
