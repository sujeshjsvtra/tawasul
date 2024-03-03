package com.tawasul.web.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import com.tawasul.web.model.Consultation;
import org.hibernate.Session;

import com.tawasul.web.model.Sector;
import com.tawasul.web.util.HibernateUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.Query;

@ApplicationScoped
@Named
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class ConsultationService implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Consultation> consultations;

	@Inject
	private Session session;

	@Inject
	private HibernateUtil hibernateUtil;

	@PostConstruct
	public void init() {
		setConsultations(populateConsultations());
	}

	public List<Consultation> populateConsultations() {
		session = hibernateUtil.getSessionFactory().openSession();

		Query query = session.createQuery( "from Consultation where status <> :status");
		query.setParameter( "status", "D" );
		List<Consultation> consultations = query.list();

		setConsultations(consultations);
		session.close();
		return consultations;
	}

	public Consultation fetchConsultationById(Long id) {
		session = hibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();

		Consultation consultation = session.get(Consultation.class, id);

		session.close();
		return consultation;
	}

	public void saveOrUpdateConsultation(Consultation existingConsultation, String name, String topic,
			String description, LocalDate startDate, LocalDate endDate, Sector sector, String status) {
		session = hibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		if (existingConsultation == null) {
			Consultation consultation = new Consultation();
			consultation.setName(name);
			consultation.setTopic(topic);
			consultation.setDescription(description);
			consultation.setStatus(status);
			consultation.setStartDate(startDate);
			consultation.setEndDate(endDate);
			consultation.setSector(sector);

			session.saveOrUpdate(consultation);
		} else {
			System.out.println("Updating existing consultation: " + existingConsultation);
			session.saveOrUpdate(existingConsultation);
		}

		session.getTransaction().commit();
		session.close();
	}

	public void deleteConsultation(Consultation consultation) {
		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		consultation.setStatus("D");

		System.out.println("Delete consultation: " + consultation);
		session.update(consultation);
		session.getTransaction().commit();
		session.close();
	}
}
