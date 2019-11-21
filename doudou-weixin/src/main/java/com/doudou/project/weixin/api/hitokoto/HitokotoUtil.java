package com.doudou.project.weixin.api.hitokoto;

import com.doudou.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Description: 一言 一句话 api https://v1.hitokoto.cn
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/21
 * @Time:21:48
 */
@Service
public class HitokotoUtil {
    private static  final  String HITOKOTO_API_URL="https://v1.hitokoto.cn";

    public  String sendMessage(){
       JSONObject jsonObject =  WeixinUtil.httpRequest(HITOKOTO_API_URL,"GET",null);
        String result = (String) jsonObject.get("hitokoto");
        return result;
    }
}
