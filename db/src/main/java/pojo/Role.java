package pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String name;
    private String permissions;
    private String permissionId;
    private String permissionName;
}
