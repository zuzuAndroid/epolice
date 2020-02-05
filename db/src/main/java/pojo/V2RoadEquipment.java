package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class V2RoadEquipment implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String assetNo;
    private String assetName;
    private String assetCode;
    private String assetType;
    private String assetIp;
    private String company;
    private int valide;
    private String remark;
}
