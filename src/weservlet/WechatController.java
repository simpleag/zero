package weservlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import method.MyService;

import org.apache.log4j.Logger;

import com.ifp.wechat.constant.ConstantWeChat;
import com.ifp.wechat.entity.message.resp.Article;
import com.ifp.wechat.entity.message.resp.NewsMessage;
import com.ifp.wechat.entity.message.resp.TextMessage;
import com.ifp.wechat.service.MessageService;
import com.ifp.wechat.util.MessageUtil;

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
					article.setTitle("é´ææ§¸æ¶ï¿½éâ³å´é¥ç¬æå¨å ä¼");
					article.setDescription("é´ææ§¸é»å¿å ªæ·âä¼");
					article.setPicUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
					article.setUrl("https://www.baidu.com/");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageService.bulidSendMessage(newsMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
				}else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("é´ææ§¸æ¶ï¿½éâ³î¿é¥ç¬æå¨å ä¼");
					article1.setDescription("");
					article1.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20130913/2013091314543416032.jpg");
					article1.setUrl("http://tuposky.iteye.com/blog/2008583");

					Article article2 = new Article();
					article2.setTitle("å¯°î»ä¿éî¿ç´¬éªå²å½´å¯®ï¿½éææç»å©®avaéå ¬ç´æµå²ç´éºã¥å½é°å¶ç ");
					article2.setDescription("");
					article2.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20131021/2013102111243367254.jpg");
					article2.setUrl("http://tuposky.iteye.com/blog/2008655");

					Article article3 = new Article();
					article3.setTitle("å¯°î»ä¿éî¿ç´¬éªå²å½´å¯®ï¿½éææç»å©®avaéï¿½(æ¶ï¿½) å¨å ä¼éºã¦æ¹éå±½å½é«ï¿½");
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
					respContent = "éå¯¸çéæ æ®éç°ç´¡ç¹çµå¹éä¹n";
					StringBuffer contentMsg = new StringBuffer();
					contentMsg.append("1\n");
					respContent += contentMsg.toString();
					textMessage.setContent(respContent);
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				}
			} else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_VOICE)) {
				textMessage.setContent("é®ã¨î©é¨å¬æ§¸éï¿½" + requestMap.get("Recognition"));
				respMessage = MessageService.bulidSendMessage(textMessage,
						ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
			} else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				if (eventType.equals(ConstantWeChat.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "éè«æå¨´å¬­ç¯éä¹n";
					StringBuffer contentMsg = new StringBuffer();
					contentMsg.append("é®ã¨ç¹éîäºé¥ç²î²æ¶å¬ªåªéæ¿ç§éå±¼ç¶æ¥ å²æµæ´ææ¹éï¿½").append("\n\n");
					contentMsg.append("1  é´ææ°¨éîéå¨´å¬­ç¯é¨ï¿½").append("\n");
					contentMsg.append("2  é´ææé®è¥æ¹ªéï¿½").append("\n");
					contentMsg.append("3  é´ææ§¸æ¾¶æ°¬æµéï¿½").append("\n");
					respContent = respContent + contentMsg.toString();
					textMessage.setContent(respContent);
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				} else if (eventType
						.equals(ConstantWeChat.EVENT_TYPE_UNSUBSCRIBE)) {
					respContent = "éæ ¨ç§·éè«æ";
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
					} else if (eventKey.equals("12")) {
						Article article = new Article();
						article.setTitle("é´ææ§¸æ¶ï¿½éâ³å´é¥ç¬æå¨å ä¼");
						article.setDescription("é´ææ§¸é»å¿å ªæ·âä¼");
						article.setPicUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
						article.setUrl("https://www.baidu.com/");
						articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageService.bulidSendMessage(newsMessage,
								ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
					}else if (eventKey.equals("help") || eventKey.equals("teacherhelp") || eventKey.equals("studenthelp")) {
						
					}else if(eventKey.equals("tacher1")){
						
					}else if (eventKey.equals("teacher2")) {
						
					}else if (eventKey.equals("teacher3")) {
						
					}else if (eventKey.equals("student1")) {
						
					}else if (eventKey.equals("student1")) {
						
					}else if (eventKey.equals("student1")) {
					
					/*绑定*/
					}else if (eventKey.equals("bind")) {
						new MyService().setUserTag(requestMap.get("FromUserName"), 109);
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
