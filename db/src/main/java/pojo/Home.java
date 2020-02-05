package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Home implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int total;
    private int passTotal;
    private int invalidTotal;
    private int activeTotal;

    private String datetime;
}
