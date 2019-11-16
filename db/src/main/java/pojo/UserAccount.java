package pojo;

import lombok.Data;

@Data
public class UserAccount {

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
