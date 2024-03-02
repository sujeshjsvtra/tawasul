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
		System.out.println("preDestory");
	}

	public void saveSector() {
		if (StringUtils.isNotBlank(this.getSectorName())) {
			sectorService.saveSector(this.getSectorName(), this.getSectorNameArabic(), "A");
			resetSectorForm();
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved successfully");
		} else {
			//new FacesMessage(FacesMessage.SEVERITY_WARN, "Failure", "Sector Name is required");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sector Name is required", "Sector Name is required"));
		}
	}

	public void resetSectorForm()
	{
		setSectorName("");
		setSectorNameArabic("");
	}
}
