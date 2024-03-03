package com.tawasul.web.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.query.Query;

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
public class SectorService implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sector> sectors;

	//@ManagedProperty(value="#{session}")
	private Session session;

	//@ManagedProperty(value="#{hibernateUtil}")
	@Inject
	private HibernateUtil hibernateUtil;

	@PostConstruct
	public void init() {
		setSectors(populateSectors());
	}

	public List<Sector> populateSectors() {
		session = hibernateUtil.getSessionFactory().openSession();

		Query query = session.createQuery( "from Sector where status <> :status");
		query.setParameter( "status", "D" );
		List<Sector> sectors = query.list();

		setSectors(sectors);
		session.close();
		return sectors;
	}

	public void saveOrUpdateSector(Sector existingSector, String sectorName, String sectorNameArabic, String status) {

		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Sector sector = new Sector();
		sector.setName(sectorName);
		sector.setArabicName(sectorNameArabic);
		sector.setStatus(status);

        if (existingSector == null) {
            session.saveOrUpdate(sector);
        } else {
			existingSector.setName(sectorName);
			existingSector.setArabicName(sectorNameArabic);
			existingSector.setStatus(status);
            session.saveOrUpdate(existingSector);
        }

        session.getTransaction().commit();
		session.close();
	}

	public void deleteSector(Sector sector) {
		session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		sector.setStatus("D");
		session.update(sector);
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
