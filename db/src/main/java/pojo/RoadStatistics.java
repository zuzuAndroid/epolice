package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoadStatistics implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private String roadName;
    private int total;
    private String company;
}
