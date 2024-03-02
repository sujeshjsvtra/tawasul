package com.tawasul.web.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;

import com.tawasul.web.model.Consultation;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
		session = hibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();

		//CriteriaQuery<Sector> criteria = builder.createQuery(Sector.class);
		//criteria.from(Sector.class);

		Query query = session.createQuery( "from Sector where status <> :status");
		long i = 0;
		query.setParameter( "status", "D" );
		List<Sector> sectors = query.list();

		//List<Sector> sectors = session.createQuery(criteria).getResultList();

		setSectors(sectors);
		session.close();
		return sectors;
	}

	public void saveSector(String sectorName, String sectorNameArabic, String status) {
		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Sector sector = new Sector();
		sector.setName(sectorName);
		sector.setArabicName(sectorNameArabic);
		sector.setStatus(status);

		session.save(sector);
		session.getTransaction().commit();
		session.close();
	}

	public void deleteSector(Sector sector) {
		session = hibernateUtil.getSessionFactory().openSession();

		sector.setStatus("D");
		session.saveOrUpdate(sector);
		session.getTransaction().commit();
		session.close();
	}

	public Sector fetchSectorById(Long id) {
		session = hibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();

		Sector sector = session.get(Sector.class, id);

		session.close();
		return sector;
	}
}
