package com.tawasul.web.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tawasul.web.model.User;

public class DBUtil {

	public static Session session;


	public static <T> void saveOrUpdate(T entity) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
    public static List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	CriteriaBuilder builder = session.getCriteriaBuilder();
    		CriteriaQuery<User> criteria = builder.createQuery(User.class);
    		criteria.from(User.class);
    		List<User> users = session.createQuery(criteria).getResultList();
    		return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static  User getUserByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	CriteriaBuilder builder = session.getCriteriaBuilder();
    		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
    		Root<User> root = criteriaQuery.from(User.class);
    		Predicate predicate = builder.equal(root.get("email"),email);
    		criteriaQuery.where(predicate);
    		User user = (User) session.createQuery(criteriaQuery).getSingleResult();
    		return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
