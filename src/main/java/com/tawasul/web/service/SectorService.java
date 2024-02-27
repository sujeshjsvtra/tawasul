package com.tawasul.web.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.tawasul.web.model.User;
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
public class SectorService implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sector> sectors;

	@Inject
	private Session session;

	@Inject
	private HibernateUtil hibernateUtil;

	@PostConstruct
	public void init() {
		setSectors(populateSectors());
	}

	public List<Sector> populateSectors() {
		System.out.println("Sector Service load");
		session = hibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Sector> criteria = builder.createQuery(Sector.class);
		criteria.from(Sector.class);
		List<Sector> sectors = session.createQuery(criteria).getResultList();

		System.out.println("Size of sector list is " + sectors.size());
		setSectors(sectors);
		session.close();
		return sectors;
	}

	public void saveSector(String sectorName, String sectorNameArabic, String status) {
		session = hibernateUtil.getSessionFactory().openSession();
		System.out.println("Saving in service");
		session.beginTransaction();
		Sector sector = new Sector();
		sector.setName(sectorName);
		sector.setArabicName(sectorNameArabic);
		sector.setStatus(status);

		session.save(sector);
		session.getTransaction().commit();
		session.close();
	}
}
