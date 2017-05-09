package weservlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import method.MyService;

import org.apache.log4j.Logger;

import wxmethod.WxService;

import com.ifp.wechat.constant.ConstantWeChat;
import com.ifp.wechat.entity.message.resp.Article;
import com.ifp.wechat.entity.message.resp.NewsMessage;
import com.ifp.wechat.entity.message.resp.TextMessage;
import com.ifp.wechat.service.MessageService;
import com.ifp.wechat.util.MessageUtil;
/*
 * 
 * */
public class WechatController {
	public static Logger log = Logger.getLogger(WechatController.class); 

	public static String processWebchatRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String msgType = requestMap.get("MsgType");
			TextMessage textMessage = (TextMessage) MessageService.bulidBaseMessage(requestMap, ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
			NewsMessage newsMessage = (NewsMessage) MessageService.bulidBaseMessage(requestMap, ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
			String respContent = "";
			List<Article> articleList = new ArrayList<Article>();
			List<TextMessage> textList = new ArrayList<TextMessage>();
			
			if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if ("tt".equals(content)) {
					Article article = new Article();
					article.setTitle("鎴戞槸涓�鏉″崟鍥炬枃娑堟伅");
					article.setDescription("鎴戞槸鎻忚堪淇℃伅");
					article.setPicUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
					article.setUrl("https://www.baidu.com/");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageService.bulidSendMessage(newsMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
				}else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("鎴戞槸涓�鏉″鍥炬枃娑堟伅");
					article1.setDescription("");
					article1.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20130913/2013091314543416032.jpg");
					article1.setUrl("http://tuposky.iteye.com/blog/2008583");

					Article article2 = new Article();
					article2.setTitle("寰俊鍏紬骞冲彴寮�鍙戞暀绋婮ava鐗堬紙浜岋級鎺ュ彛閰嶇疆 ");
					article2.setDescription("");
					article2.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20131021/2013102111243367254.jpg");
					article2.setUrl("http://tuposky.iteye.com/blog/2008655");

					Article article3 = new Article();
					article3.setTitle("寰俊鍏紬骞冲彴寮�鍙戞暀绋婮ava鐗�(涓�) 娑堟伅鎺ユ敹鍜屽彂閫�");
					article3.setDescription("");
					article3.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20131021/2013102111291287031.jpg");
					article3.setUrl("http://tuposky.iteye.com/blog/2017429");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageService.bulidSendMessage(newsMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
				}else if ("z".equals(content)) {
					TextMessage t1 = new TextMessage();
					t1.setFromUserName(requestMap.get("ToUserName"));
					t1.setToUserName(requestMap.get("FromUserName"));
					Date date = new Date();
					long time = date.getTime();
					t1.setCreateTime(time);
					t1.setContent("zeta");
					t1.setMsgType("text");
					respMessage = MessageService.bulidSendMessage(t1,ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				}else if("zz".equals(content)){
					respContent = "鏇寸畝鍗曠殑鏂瑰紡瀹炵幇锛乗n";
					StringBuffer contentMsg = new StringBuffer();
					contentMsg.append("1\n");
					respContent += contentMsg.toString();
					textMessage.setContent(respContent);
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				}
			} else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_VOICE)) {
				textMessage.setContent("鎮ㄨ鐨勬槸锛�" + requestMap.get("Recognition"));
				respMessage = MessageService.bulidSendMessage(textMessage,
						ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
			} else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				if (eventType.equals(ConstantWeChat.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "欢迎关注微信号";
					StringBuffer contentMsg = new StringBuffer();
					contentMsg.append("按帮助跳出帮助选项").append("\n\n");
					contentMsg.append("绑定进行用户绑定").append("\n");
					
					respContent = respContent + contentMsg.toString();
					textMessage.setContent(respContent);
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				} else if (eventType
						.equals(ConstantWeChat.EVENT_TYPE_UNSUBSCRIBE)) {
					textMessage.setContent(respContent);
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				} else if (eventType.equals(ConstantWeChat.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					if (eventKey.equals("11")) {
						respContent = "11";
						textMessage.setContent(respContent);
						respMessage = MessageService.bulidSendMessage(textMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
					}else if (eventKey.equals("help") || eventKey.equals("teacherhelp1") || eventKey.equals("studenthelp")) {
						respContent = "此为帮助信息！\n";
						/*-把文本内容放到了Content中具体为 <Content><![CDATA[内容]]></Content>-*/
						StringBuffer contentMsg = new StringBuffer();
						contentMsg.append("点击绑定后成为正式用户享受更多服务").append("\n\n");
						contentMsg.append("教师功能包括：群发信息给学生、查看历史信息、查看作为教师的课程").append("\n");
						contentMsg.append("学生功能包括：查看历史信息、查看课表、查看教师").append("\n");
						contentMsg.append("教师群发信息后会自动推送给学生").append("\n");
						contentMsg.append("查看信息会收到一个文字信息，点击后进入页面").append("\n");
						/*-添加到回复中-*/
						respContent = respContent + contentMsg.toString();
						textMessage.setContent(respContent);
						respMessage = MessageService.bulidSendMessage(textMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
					}else if(eventKey.equals("tacherg1")){
						Article article = new Article();
						article.setTitle("发送消息");
						article.setDescription("点此进行消息发送");
						article.setPicUrl("");
						article.setUrl("http://lalalaleo.com/wechat/wechat/mTeacherMessage.html?openid="+requestMap.get("openid"));
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}else if (eventKey.equals("teacherg2")) {
						Article article = new Article();
						article.setTitle("历史消息");
						article.setDescription("点此进行查看历史消息");
						article.setPicUrl("");
						article.setUrl("");
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}else if (eventKey.equals("teacherg3")) {
						/*此处需要classid*/
						String classid = new String();
						Article article = new Article();
						article.setTitle("查看我的课程");
						article.setDescription("点此查看我的课程");
						article.setPicUrl("");
						article.setUrl("http://lalalaleo.com/wechat/wechat/schoolTimeTable.html?openid="+requestMap.get("openid"));
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}else if (eventKey.equals("student1")) {
						String classid = new String();
						Article article = new Article();
						article.setTitle("查看历史信息");
						article.setDescription("点此查看我的历史信息");
						article.setPicUrl("");
						article.setUrl("");
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}else if (eventKey.equals("student2")) {
						String classid = new String();
						Article article = new Article();
						article.setTitle("查看我的教师");
						article.setDescription("点此查看我的教师");
						article.setPicUrl("");
						article.setUrl("http://lalalaleo.com/wechat/wechat/teacherList.html?openid="+requestMap.get("openid"));
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}else if (eventKey.equals("student3")) {
						String classid = new String();
						Article article = new Article();
						article.setTitle("查看我的课表");
						article.setDescription("点此查看我的课表");
						article.setPicUrl("");
						article.setUrl("http://lalalaleo.com/wechat/wechat/schoolTimeTable.html?openid="+requestMap.get("openid"));
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					/*绑定 意添加openid*/
					}else if (eventKey.equals("bind")) {
						
						Article article = new Article();
						article.setTitle("绑定申请");
						article.setDescription("点此进行绑定");
						article.setPicUrl("");
						article.setUrl("http://lalalaleo.com/wechat/wechat/binding.html?openid="+requestMap.get("openid"));
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(respMessage);
		return respMessage;
	}
}
