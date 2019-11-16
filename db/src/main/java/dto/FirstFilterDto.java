package dto;

import lombok.Data;

@Data
public class FirstFilterDto {

    private String username;
    private String startDate;
    private String endDate;
    private int status;//-1:未筛选，0:通过，1:作废
    private String illegalCode;
    private int limit;
    private int pageNum;
    private int pageSize;
    private int period;//一期，二期
    private String licenseNumber;//号牌号码
    private String road;//违法地点
    private String hpzl;//号牌种类
}