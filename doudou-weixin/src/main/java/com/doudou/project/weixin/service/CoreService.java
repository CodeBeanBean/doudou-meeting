package com.doudou.project.weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.doudou.po.User;
import com.doudou.po.WeiUser;
import com.doudou.project.weixin.api.accesstoken.AccessTokenRedis;
import com.doudou.project.weixin.api.hitokoto.HitokotoUtil;
import com.doudou.project.weixin.api.ownthink.OwnThinkBean;
import com.doudou.project.weixin.api.ownthink.OwnThinkUtil;
import com.doudou.project.weixin.api.tuling.TulingUtil;
import com.doudou.project.weixin.api.userinfo.UserInfoService;
import com.doudou.project.weixin.main.MenuManager;
import com.doudou.project.weixin.pojo.AccessToken;
import com.doudou.service.UserService;
import com.doudou.service.WeiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doudou.project.weixin.util.MessageUtil;


import com.doudou.project.weixin.bean.resp.Article;
import com.doudou.project.weixin.bean.resp.NewsMessage;
import com.doudou.project.weixin.bean.resp.TextMessage;

@Service
public class CoreService {
	//图灵api

	@Autowired
	private TulingUtil tulingUtil;

	//一言api

	@Autowired
	private HitokotoUtil hitokotoUtil; //一言

    @Autowired
	private AccessTokenRedis accessTokenRedis; //redis accseetoken 对象
	@Autowired
	private OwnThinkUtil ownThinkUtil; //思知机器人
	@Autowired
	private UserInfoService userInfoService; //收集个人信息
	@Autowired
	private UserService userService;
	@Autowired
	private WeiUserService weiUserService;
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id） 下面三行代码是： 从HashMap中取出消息中的字段；
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息 组装要返回的文本消息对象；
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			// textMessage.setContent("欢迎访问<a
			// href=\"http://www.baidu.com/index.php?tn=site888_pg\">百度</a>!");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			/**
			 * 演示了如何接收微信发送的各类型的消息，根据MsgType判断属于哪种类型的消息；
			 */

			// 接收用户发送的文本消息内容
			String content = requestMap.get("Content");

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(System.currentTimeMillis());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				//respContent = tulingUtil.sendMessage(content);
				respContent = ownThinkUtil.sendMessage(content,toUserName);
				//respContent = "您发送的是文本消息！";
				System.out.println("accesstoken值是"+accessTokenRedis.getAccessTokenVal());
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					/**
					 * 手机用户的个人信息
					 * 1得到用户的openid
					 * 2根据openid向微信服务器发送get请求得到用户信息
					 * 3得到用户是jsonobject对象-》转成weiuser对象
					 * 4将weiuser对象进行数据库的添加操作
					 */

					userInfoService.userInfo(fromUserName);
					respContent = "欢迎关注微信公众号";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("11")) {

						List<Article> articleList = new ArrayList<Article>();
						Article article = new Article();
						//	respContent = "会议抢单项被点击！";
						//通过openid (fromUserName)判断当前用户是否进行绑定功能
					User user = userService.selectBtOpenid(fromUserName);
					if(user == null){//如果没有进行登录功能-》跳登陆页面
					WeiUser weiUser = weiUserService.selectByOpenid(fromUserName);
						article.setTitle("您还未登录");
						article.setDescription("该功能需要登陆后才可访问，点击进入登陆页面");
						article.setPicUrl(
								"http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=1653962122,497205422&os=3275311409,3410173034&simid=3141220,535664108&pn=68&rn=1&di=221540&ln=1706&fr=&fmq=1575027941098_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fimage2.sina.com.cn%2Fent%2Fd%2F2005-06-21%2FU105P28T3D758553F326DT20050621155942.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined");
						article.setUrl(MenuManager.REAL_URL+"user/to/login?wid="+weiUser.getId());

					}else {//已经绑定 判断角色是否是发单组/抢单组
						if(user.getRid()==1){//发单组 无权限
							article.setTitle(user.getName()+"您是发单组,无法操作该功能");
							article.setDescription("该功能需要抢单组权限才可访问，点击进入登陆页面");
							article.setPicUrl("http://photocdn.sohu.com/20100902/Img274653008.jpg");
							article.setUrl(MenuManager.REAL_URL+"user/to/unauth");

						}else {//抢单组 进入抢单页面
							article.setTitle(user.getName()+"欢迎您,进入抢单功能");
							article.setDescription("抢单功能是什么，点击进入抢单教程");
							article.setPicUrl("http://photocdn.sohu.com/20100902/Img274653008.jpg");
							article.setUrl(MenuManager.REAL_URL+"user/to/meetingGrab?uid="+user.getId());
						}
						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;
					}

					}else if (eventKey.equals("31")){
						respContent = "联系我们项被点击！";
					}else if (eventKey.equals("34")){
						//respContent = "随机一言项被点击！";
						respContent = hitokotoUtil.sendMessage();
					} else if (eventKey.equals("70")) {

						List<Article> articleList = new ArrayList<Article>();
						
						// 单图文消息
						Article article = new Article();
						article.setTitle("标题");
						article.setDescription("描述内容");
						article.setPicUrl("图片链接");
						article.setUrl("跳转链接");

						
						articleList.add(article);						
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;
					}
					
				}

			}

			// 组装要返回的文本消息对象；
			textMessage.setContent(respContent);
			// 调用消息工具类MessageUtil将要返回的文本消息对象TextMessage转化成xml格式的字符串；
			respMessage = MessageUtil.textMessageToXml(textMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

}
