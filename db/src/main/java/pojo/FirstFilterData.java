package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class FirstFilterData {

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
    private String sfsh;
    private String username;
    private Date screendate;
    private String csmz;
    private int sjzt;
    private String roadName;
    private String licenseType;
    private int period = 1;
}
