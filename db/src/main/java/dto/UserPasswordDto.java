package dto;

import lombok.Data;

@Data
public class UserPasswordDto {

    private int id;
    private String oldPwd;
    private String newPwd;
}
