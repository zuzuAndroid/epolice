package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class CheckFilter {

    private int id;
    private String msgid;
    private String hphm;
    private String hpzl;
    private String cjsj;
    private String wfdm;
    private String cjdz;
    private String sbbh;
    private String url1;
    private String username;
    private Date screendate;
    private int status;
    private int uploadStatus;
    private Date uploadDatetime;
    private int period;
}
