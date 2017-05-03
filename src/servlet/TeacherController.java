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

import model.*;
import dao.*;

@Controller
public class TeacherController {
	@RequestMapping(value = "findteacher", method = RequestMethod.POST)
	public void FindTeacher (People people, HttpServletRequest request, HttpServletResponse response){
		PeopleDao peoDao = new PeopleDao();
		SubjectDao subDao = new SubjectDao();
		PrintWriter printWriter = null;
		
		try {
			People peopleFind = peoDao.findByOpenId(people);
			List<Subject> subjectList = subDao.listByTeaId(peopleFind.getUseId());
			List<String> subNameList = new ArrayList<String>();
			if(peopleFind != null && subjectList != null){
				for(int i = 0; i < subjectList.size(); i++){
					subNameList.add(subjectList.get(i).getSubName());
				}
				Map<String, Object> mapResult = new HashMap<String, Object>();
				mapResult.put("openid", peopleFind.getOpenId());
				mapResult.put("name", peopleFind.getUseName());
				mapResult.put("info", "ȱʧ");
				mapResult.put("classes", subNameList);
				mapResult.put("mobile", "ȱʧ");
				mapResult.put("email", "ȱʧ");
				String jsonResult = JSON.toJSONString(mapResult);
				printWriter = response.getWriter();
				printWriter.print(jsonResult);
			}
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
