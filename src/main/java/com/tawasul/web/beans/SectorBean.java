package com.tawasul.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.tawasul.web.util.MessageUtil;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import com.tawasul.web.model.Sector;
import com.tawasul.web.service.SectorService;
import com.tawasul.web.util.HibernateUtil;


@RequestScoped
@Named
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SectorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sector> sectors;
	private Sector sector;
	private String sectorName;
	private String sectorNameArabic;
	private List<Sector> selectedSectors;

	@Inject
	private SectorService sectorService;

	@PostConstruct
	public void init() {
		sectorService = new SectorService();
		setSectors(sectorService.populateSectors());
		resetSectorForm();
	}

	@PreDestroy
	public void preDestory() {
	}

	public void saveSector() {
		if (StringUtils.isNotBlank(this.getSectorName())) {
			sectorService.saveSector(this.getSectorName(), this.getSectorNameArabic(), "A");
			resetSectorForm();
			MessageUtil.info("Saved successfully");
		} else {
			MessageUtil.error("Sector Name is required");
		}
	}

	public void resetSectorForm()
	{
		setSectorName("");
		setSectorNameArabic("");
	}
}
