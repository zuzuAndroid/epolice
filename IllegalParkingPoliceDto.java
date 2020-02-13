package dto;

import lombok.Data;

@Data
public class IllegalParkingPoliceDto {

    private String username;//筛选人
    private String startDate;//开始时间
    private String endDate;//结束时间
    private Integer status = null;//-1:未筛选，0:通过，1:作废
    private String illegalCode;//违法代码
    private int pageNum;//第几页
    private int pageSize;//每页几条
    private int period;//一期or二期
    private String hphm;//号牌号码
    private String hpzl;//号牌种类
    private String cjdz;//采集地址
    private String ddname;//采集机关
    private int type;//1、海康   2、人工录入
}
