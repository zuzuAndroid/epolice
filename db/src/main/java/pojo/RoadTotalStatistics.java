package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoadTotalStatistics implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private String cjdz;
    private int total;
    private int alogTotal;
    private int peopleTotal;
    private int reviewTotal;
}
