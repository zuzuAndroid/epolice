package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarType implements Serializable {

    private int id;
    private String code;
    private String name;
}
