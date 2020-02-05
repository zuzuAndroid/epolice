package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FirstFilterStatistics implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private String username;
    private int total;
    private int passTotal;
    private int invalidTotal;
    private int pickupTotal;
}
