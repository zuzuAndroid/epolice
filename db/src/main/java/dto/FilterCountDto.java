package dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class FilterCountDto implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int pickupTotal;
    private int todayPass;
    private int todayInvalid;
}
