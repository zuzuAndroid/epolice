package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoadEquipment implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private String id;
    private String name;
    private int show;
    private int total;
}
