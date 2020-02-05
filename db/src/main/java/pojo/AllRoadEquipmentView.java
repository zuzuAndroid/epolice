package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AllRoadEquipmentView implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private String code;
    private String name;
    private int valid;
    private int period;
    private int total;
}
