package com.doudou.project.weixin.api.tuling;

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
 * @Date:2019/11/21
 * @Time:16:51
 */
@Service
public class TulingUtil {
    private static final String TULING_API_URL="http://openapi.tuling123.com/openapi/api/v2";

    /** ######### TODO 发送消息和响应消息
     * @InputParam msg 用户发送的文本
     * @OutputParam result 响应的文本
     */
    public String sendMessage(String msg){


        String[] str = { "f94f1aa826bf48c7ad10e822c52c1ff3",
                        "59612aa131604596ba64b222bada9e99",
                        "27f773a776374a1f92c0c53fd6e5c5e2",
                        "0877647621004661be403d20219d5367",
                        "a130797080e3431d88be88e49a1f2991"};
        int i = 0;

   /*   f94f1aa826bf48c7ad10e822c52c1ff3
        59612aa131604596ba64b222bada9e99
        27f773a776374a1f92c0c53fd6e5c5e2
        0877647621004661be403d20219d5367
        a130797080e3431d88be88e49a1f2991*/

        //生成入参的JSON对象
        while(true){

        JSONObject jsonObject= sendJSONObject(msg,str[i]);

        //2步  对指定的API 地址 发送POST请求 （传入入参JSON对象）
        JSONObject object= WeixinUtil.httpRequest(TULING_API_URL,"POST",jsonObject.toString());

        System.out.println("响应的消息："+object.toString());
        JSONArray  array= (JSONArray) object.get("results");

        JSONObject object1= (JSONObject) array.get(0);
        JSONObject object2= (JSONObject) object1.get("values");

        String result= (String) object2.get("text");

        if (result.equals("请求次数超限制!")){
            i++;

            if (i==5){
                return result;
            }
            continue;
        }

        return result;
        }
    }
    /** ###########TODO 生成入参JSON对象
     * 按规则生成入参的JSON对象
     * @param msg 用户发送的文本
     * @param apiKey 机器人的apiKey
     * @return
     */
    public JSONObject  sendJSONObject(String msg,String apiKey){
        //JSON格式数据（入参）
        InputText inputText=new InputText();
        inputText.setText(msg);
        Perception perception=new Perception();
        perception.setInputText(inputText);

        UserInfo userInfo=new UserInfo();
        userInfo.setApiKey(apiKey);
        userInfo.setUserId("doudou");

        TulingBean tulingBean=new TulingBean();
        tulingBean.setPerception(perception);
        tulingBean.setUserInfo(userInfo);

        JSONObject jsonObject=JSONObject.fromObject(tulingBean);
        return jsonObject;
    }

    /*public static void main(String[] args) {

         String api="http://openapi.tuling123.com/openapi/api/v2";
         String msg = "你好";//用户输入的文本
        //Json 格式数据（入参）
        InputText inputText = new InputText();
        inputText.setText(msg);

        Perception perception = new Perception();
        perception.setInputText(inputText);

        UserInfo userInfo = new UserInfo();
        userInfo.setApiKey("f94f1aa826bf48c7ad10e822c52c1ff3");
        userInfo.setUserId("doudou");

        TulingBean tulingBean = new TulingBean();
        tulingBean.setPerception(perception);
        tulingBean.setUserInfo(userInfo);

        JSONObject jsonObject =JSONObject.fromObject(tulingBean);
        System.out.println(jsonObject.toString());

        //2 对指定api地址发送post请求（传入入参json对象
       JSONObject object =  WeixinUtil.httpRequest(api,"POST",jsonObject.toString());
        System.out.println("响应数据"+object.toString());

        JSONArray array = (JSONArray) object.get("results");

        JSONObject object1 = (JSONObject) array.get(0);
        JSONObject object2 = (JSONObject) object1.get("values");
        String result = (String) object2.get("text");
        System.out.println("返回的结果是"+result);
    }*/
}
