package com.tawasul.web.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.Sector;
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

	public static User getUserByEmail(String email) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			Predicate predicate = builder.equal(root.get("email"), email);
			criteriaQuery.where(predicate);
			User user = (User) session.createQuery(criteriaQuery).getSingleResult();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Consultation> getAllActiveConsultations() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Consultation> criteriaQuery = builder.createQuery(Consultation.class);
			Root<Consultation> root = criteriaQuery.from(Consultation.class);
			Predicate predicate = builder.notEqual(root.get("status"), "D");
			criteriaQuery.where(predicate);
			List<Consultation> consultations = session.createQuery(criteriaQuery).getResultList();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Sector> getAllSectors() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Sector> criteria = builder.createQuery(Sector.class);
			criteria.from(Sector.class);
			List<Sector> sectors = session.createQuery(criteria).getResultList();
			return sectors;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Consultation> getAllOpenConsultations() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Consultation> criteriaQuery = builder.createQuery(Consultation.class);
			Root<Consultation> root = criteriaQuery.from(Consultation.class);
			Predicate predicate = builder.equal(root.get("status"), "O");
			criteriaQuery.where(predicate);
			List<Consultation> consultations = session.createQuery(criteriaQuery).getResultList();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Consultation> getAllClosedConsultations() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Consultation> criteriaQuery = builder.createQuery(Consultation.class);
			Root<Consultation> root = criteriaQuery.from(Consultation.class);
			Predicate predicate = builder.equal(root.get("status"), "C");
			criteriaQuery.where(predicate);
			List<Consultation> consultations = session.createQuery(criteriaQuery).getResultList();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Consultation> getAllConsultationByFilter(Integer type, Long sectorId, Date startDate,
			Date endDate) {
		System.out.println(sectorId);
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Consultation> criteriaQuery = criteriaBuilder.createQuery(Consultation.class);
			Root<Consultation> root = criteriaQuery.from(Consultation.class);
			List<Predicate> predicates = new ArrayList<>();
			if (type != 2) {
				predicates.add(criteriaBuilder.equal(root.get("status"), type == 1 ? "O" : "C"));
			}
			if (sectorId != null) {
				predicates.add(criteriaBuilder.equal(root.get("sector"), sectorId));
			}
			if (startDate != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startdate"), startDate));
			}
			if (endDate != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("enddate"), endDate));
			}
			Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			criteriaQuery.where(finalPredicate);
			List<Consultation> consultations = session.createQuery(criteriaQuery).getResultList();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
