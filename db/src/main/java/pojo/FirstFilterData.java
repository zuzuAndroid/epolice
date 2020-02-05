package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FirstFilterData implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int sysid;
    private String msgid;
    private int z_sysid;
    private String hpzl;
    private String hphm;
    private String cjsj;
    private String wfdm;
    private String cjdz;
    private String sbbh;
    private String url1;
    private String url2;
    private String sfsh;
    private String username;
    private String realName;
    private Date screendate;
    private String csmz;
    private int sjzt;
    private String roadName;
    private String licenseType;
    private int period = 1;
}
