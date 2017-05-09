package servlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

import dao.*;
import model.*;

@Controller
public class UserController {
	@RequestMapping(value = "isuser", method = RequestMethod.POST)
	public void IsUser (People people, HttpServletRequest request, HttpServletResponse response){
		PeopleDao peoDao = new PeopleDao();
		PrintWriter printWriter = null;

		try {
			Map<String, Object> mapResult = new HashMap<String, Object>();
			if(peoDao.isUser(people)){
				mapResult.put("result", "Yes");
			}
			else{
				mapResult.put("result", "No");
			}
			String jsonResult = JSON.toJSONString(mapResult);
			printWriter = response.getWriter();
			printWriter.print(jsonResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}
