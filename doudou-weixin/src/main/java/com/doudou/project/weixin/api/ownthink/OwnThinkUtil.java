package com.doudou.project.weixin.api.ownthink;

import com.doudou.project.weixin.api.tuling.bean.InputText;
import com.doudou.project.weixin.api.tuling.bean.Perception;
import com.doudou.project.weixin.api.tuling.bean.TulingBean;
import com.doudou.project.weixin.api.tuling.bean.UserInfo;
import com.doudou.project.weixin.util.WeixinUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/22
 * @Time:9:23
 */
@Service
public class OwnThinkUtil {
    /**
     * 请求URL：https://api.ownthink.com/bot?spoken=spoken_text
     * 请求示例 https://api.ownthink.com/bot?appid=xiaosi&userid=user&spoken=姚明多高啊？
     */

    private static  final  String OWNTHINK_API_URL="https://api.ownthink.com/bot";

    /** ######### TODO 发送消息和响应消息
     * @InputParam msg 用户发送的文本
     * @OutputParam result 响应的文本
     */
    public String sendMessage(String msg,String userId) {



            JSONObject jsonObject = sendJSONObject(msg,userId);

            //2步  对指定的API 地址 发送POST请求 （传入入参JSON对象）
            JSONObject object = WeixinUtil.httpRequest(OWNTHINK_API_URL, "POST", jsonObject.toString());

            System.out.println("响应的消息：" + object.toString());

           //JSONObject object = (JSONArray) object.get("results");

            JSONObject object1 = (JSONObject) object.get("data");
            JSONObject object2 = (JSONObject) object1.get("info");

            String result = (String) object2.get("text");



            return result;
        }


    /** ###########TODO 生成入参JSON对象
     * 按规则生成入参的JSON对象
     * @param  spoken 用户发送的文本
     * @param  userid 用户id
     * @return
     */
    public JSONObject  sendJSONObject(String spoken,String userid){
        //JSON格式数据（入参）

        OwnThinkBean ownThinkBean = new OwnThinkBean();
        ownThinkBean.setSpoken(spoken);

        ownThinkBean.setUser("");

        JSONObject jsonObject=JSONObject.fromObject(ownThinkBean);
        return jsonObject;
    }
}
