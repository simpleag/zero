package wxmethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;



import com.ifp.wechat.entity.AccessToken;
import com.ifp.wechat.util.WeixinUtil;
/**
 * acessToken 保存微信的Acess_Token 用以进行操作
 * lastAccessTokenEndTime 上一次申请AcessToken的时间
 * expireTime 设置一个Acess_Token的保存时间 时间为2000秒 微信Acess_Token保存时间最长为7200秒 避免极限设置为2000秒
 * 获取lastAcessTokenEndTime时自动加上保存时间 
 * getNAccessToken(当前时间) 判断之前申请Access_Token是否过期 若过期或者不存在就取新的 
 */
public class WxHttpMethod {
	private static String acessToken = "";
	private static Long lastAccessTokenEndTime = (long) 0;
	private static int expireTime = 2000 * 1000;
	public static String getAcessToken() {
		return acessToken;
	}
	public static void setAcessToken(String acessToken) {
		WxHttpMethod.acessToken = acessToken;
	}
	public static Long getLastAccessTokenEndTime() {
		return (long)expireTime+lastAccessTokenEndTime;
	}
	public static void setLastAccessTokenEndTime(Long lastAccessTokenEndTime) {
		WxHttpMethod.lastAccessTokenEndTime = lastAccessTokenEndTime;
	}
	public static String getNAccessToken(long nowtime) {
		String access_token;
		if(WxHttpMethod.getLastAccessTokenEndTime() < nowtime || WxHttpMethod.getAcessToken().equals("")){
			AccessToken ac = WeixinUtil.getAccessToken("wx7011496372902790", "22448b7ad7edf143d027144f378e2fe6");
			access_token = ac.getToken();
			WxHttpMethod.setAcessToken(ac.getToken());
			WxHttpMethod.setLastAccessTokenEndTime(nowtime);
			
		}else{
			access_token = WxHttpMethod.getAcessToken();
		}
		return access_token;
	}
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			//            String urlNameString = url + "?" + param;
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
}
