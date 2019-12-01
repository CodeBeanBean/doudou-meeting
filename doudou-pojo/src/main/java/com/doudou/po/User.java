package com.doudou.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {
    private String id;

    private String name;

    private String email;

    private String telephone;

    private String province;

    private String city;

    private String zone;

    private Integer rid;

    private Date createdate;

    private Short status;

    private Integer wid;


}