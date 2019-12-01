package com.doudou.po;


import lombok.Data;

import java.io.Serializable;

@Data
public class WeiUser implements Serializable {
    private Integer id;

    private String openid;

    private String nickname;

    private Integer sex;

    private String city;

    private String country;

    private String province;

    private String headimgurl;

    private Short subscribe;

    private String language;

    private String remark;

    /**
     * 补充 方便 将json转成bean
     */
    private long subscribe_time;
    private String unionid;
    private int groupid;
    private String[] tagid_list;
    private String subscribe_scene;
    private String qr_scene;
    private String qr_scene_str;
 }