package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.*;

public class ChoiceDao {
	public Boolean creat(Choice choice) throws Exception{
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(choice);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public Boolean delete(Choice choice) throws Exception{
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Choice choiceFind = (Choice) session.get(Choice.class, choice.getChoId());
			if(choiceFind == null) 
				throw new Exception("������");
			session.delete(choice);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public Boolean modify(Choice choice) throws Exception{
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Choice choiceFind = (Choice) session.get(Choice.class, choice.getChoId());
			if(choiceFind == null) 
				throw new Exception("������");
			session.update(choice);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
	
	public List<Choice> list() throws Exception{
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Choice";  
	        Query query = session.createQuery(hql);  
	        List<Choice> choiceList = query.list();
			session.getTransaction().commit();
			return choiceList;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
}
