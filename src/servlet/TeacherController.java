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
	
	@RequestMapping(value = "sendfindclazz", method = RequestMethod.POST)
	public void SendFindClazz (People people, HttpServletRequest request, HttpServletResponse response){
		ClazzDao claDao = new ClazzDao();
		PrintWriter printWriter = null;
		
		try {
			List clazzList = claDao.listByTea(people.getOpenId());
			if(clazzList != null){
				List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
				for(int i = 0; i < clazzList.size(); i++){
					Object[] objects = (Object[]) clazzList.get(i);
					Clazz clazz = (Clazz) objects[0];
					Subject subject = (Subject) objects[1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", "" + subject.getSubName() + clazz.getClaTab());
					map.put("classid", clazz.getClaId());
					listResult.add(map);
				}
				String jsonResult = JSON.toJSONString(listResult);
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
	
	@RequestMapping(value = "sendfindstudent", method = RequestMethod.POST)
	public void SendFindStudent (Clazz clazz, HttpServletRequest request, HttpServletResponse response){
		PeopleDao peoDao = new PeopleDao();
		PrintWriter printWriter = null;
		
		try {
			List<People> peopleList = peoDao.listByCla(clazz.getClaId());
			if(peopleList != null){
				List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
				for(int i = 0; i < peopleList.size(); i++){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", peopleList.get(i).getUseName());
					map.put("number", peopleList.get(i).getUseNum());
					map.put("studentid", peopleList.get(i).getOpenId());
					listResult.add(map);
				}
				String jsonResult = JSON.toJSONString(listResult);
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
