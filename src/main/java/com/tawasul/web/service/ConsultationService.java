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

import com.tawasul.web.model.Consultation;
import org.hibernate.Session;

import com.tawasul.web.model.Sector;
import com.tawasul.web.util.HibernateUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApplicationScoped
@Named
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Consultation> criteria = builder.createQuery(Consultation.class);
		criteria.from(Consultation.class);
		List<Consultation> consultations = session.createQuery(criteria).getResultList();

		setConsultations(consultations);
		session.close();
		return consultations;
	}

	public void saveConsultation(String name, String topic, String description, LocalDate startDate, LocalDate endDate, Sector sector,
								 String status) {
		session = hibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Consultation consultation = new Consultation();
		consultation.setName(name);
		consultation.setTopic(topic);
		consultation.setDescription(description);
		consultation.setStatus(status);
		consultation.setStartDate(startDate);
		consultation.setEndDate(endDate);
		consultation.setSector(sector);

		session.save(consultation);
		session.getTransaction().commit();
		session.close();
	}
}
