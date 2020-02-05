package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class V2CheckpointEquipment implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String assetName;
    private String assetCode;
    private String direction;
    private String assetIp;
    private String company;
    private int scope;//1：三环内 2：三到四环 3：四环外
    private int valide;
    private String remark;
}
