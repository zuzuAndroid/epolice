package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InvalidFilter implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String msgid;
    private String hpzl;
    private String hphm;
    private String cjsj;
    private String cjdz;
    private String sbbh;
    private String wfdm;
    private String url1;
    private String url2;
    private int sfsh;
    private String username;
    private String realName;
    private Date screendate;
    private int period;
    private int sjzt;
    private String roadName;
    private String licenseType;
}
