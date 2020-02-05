package pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReviewFilter implements Serializable {

    private static final long serialVersionUID = 7967476135812239100L;

    private int id;
    private String msgid;
    private String hphm;
    private String hpzl;
    private String cjsj;
    private String wfdm;
    private String cjdz;
    private String sbbh;
    private String url1;
    private String url2;
    private int sfsh;
    private String username;
    private String realName;
    private Date screendate;
    private int uploadStatus;//上传状态 0：未上传 1：已上传
    private Date uploadDateTime;//上传成功时间
    private int period;
    private String roadName;
    private String licenseType;
}
