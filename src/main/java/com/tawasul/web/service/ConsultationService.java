package com.tawasul.web.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.File;
import com.tawasul.web.model.Sector;
import com.tawasul.web.util.HibernateUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationService implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Consultation> consultations;

	private Session session;

	@Inject
	private HibernateUtil hibernateUtil;

	@PostConstruct
	public void init() {
		setConsultations(populateConsultations());
	}

	public List<Consultation> populateConsultations() {
		session = hibernateUtil.getSessionFactory().openSession();

		Query query = session.createQuery("from Consultation where status <> :status");
		query.setParameter("status", "D");
		List<Consultation> consultations = query.list();

		setConsultations(consultations);
		session.close();
		return consultations;
	}

	public Consultation fetchConsultationById(Long id) {
		session = hibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();

		Consultation consultation = session.get(Consultation.class, id);
		//Hibernate.initialize(consultation.getSurveys());

		session.close();
		return consultation;
	}

	public void saveOrUpdateConsultation(Consultation existingConsultation, String name, String topic,
										 String description, Date startDate, Date endDate, Sector sector, File file, String status) {

		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Consultation consultation;

		if (existingConsultation == null) {
			consultation = new Consultation();

		} else {
			consultation = existingConsultation;
			System.out.println("Updating existing consultation: " + consultation);

		}

		consultation.setName(name);
		consultation.setTopic(topic);
		consultation.setDescription(description);
		consultation.setStatus(status);
		consultation.setStartDate(startDate);
		consultation.setEndDate(endDate);
		consultation.setSector(sector);
		consultation.setImage(file);

		session.saveOrUpdate(consultation);

		session.getTransaction().commit();
		session.close();
	}

	public List<File> fetchFiles(String moduleName, Long id, String fileName) {
		session = hibernateUtil.getSessionFactory().openSession();
		Query query = null;

		if(moduleName!=null) {
			query = session.createQuery("from File where module = :moduleName");
			query.setParameter("moduleName", moduleName);
		}

		if(moduleName!=null) {
			query = session.createQuery("from File where id = :id");
			query.setParameter("id", id);
		}

		List<File> files = query.list();
		session.close();
		return files;
	}

	public void saveOrUpdateFile(File file) {
		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.saveOrUpdate(file);

		session.getTransaction().commit();
		session.close();
	}

	public void deleteConsultation(Consultation consultation) {
		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		consultation.setStatus("D");

		session.update(consultation);
		session.getTransaction().commit();
		session.close();
	}
}
