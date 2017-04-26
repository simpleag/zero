package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;

import hibernate.HibernateUtil;

@Controller
public class TestController {
	@RequestMapping(value = "teacherTest", method = RequestMethod.POST)
	public void AddLabel(@RequestParam(value="a")String a, @RequestParam(value="b")String b, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(a);
		System.out.println(b);
		Map<String, Object> map1 = new HashMap<String, Object>(10);
		map1.put("openid", a);
		map1.put("name", b);
		map1.put("info", "123123");
		map1.put("classes", "123123");
		map1.put("mobile", "123123");
		map1.put("email", a);
		String jsonResult = JSON.toJSONString(map1);
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(jsonResult);
		} catch (IOException ex) {
			
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}
