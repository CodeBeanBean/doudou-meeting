package com.doudou.project.weixin.api.tuling.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/21
 * @Time:16:58
 */
@Data
public class TulingBean {

    /**
     * 输入类型 默认 0 文本 1 图片 2 音频
     */
    private  int reqType = 0;
    private Perception perception;
    private UserInfo userInfo;
}
