package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {

    private int id;
    private String name;
    private String nickname;
    private int deptId;
    private int roleId;
    private String roleName;
    private String deptName;
    private int status;
}
