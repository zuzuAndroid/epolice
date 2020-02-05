package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String name;
    private String nickname;
    private int roleId;
    private int deptId;
    private String deptName;
    private String phone;
    private String password;
    private int status;
}
