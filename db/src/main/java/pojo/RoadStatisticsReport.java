package pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoadStatisticsReport implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private String cjdz;
    private int total;
    private int alogTotal;
    private int peopleTotal;
    private int reviewTotal;

    private float alogRate = 0;//平台筛选率
    private float peopleRate = 0;//人工筛选率
    private float reviewRate = 0;//复核通过率
}
