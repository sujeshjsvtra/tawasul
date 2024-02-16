package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tawasul.web.model.Sector;
import com.tawasul.web.service.SectorService;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@ViewScoped
@Named
//JSF
//@ManagedBean(name = "sectorBean")
//@ViewScoped
public class SectorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sector> sectors;

	private Sector sector;

	private String sectorName = "";

	@Inject
	private SectorService sectorService;

	private List<Sector> selectedSectors;

	@PostConstruct
	public void init() {
		System.out.println("PostConstruct SectorBean: " + LocalDateTime.now());
		//JSF
//		sectorService = new SectorService();
//		this.sectors = this.sectorService.getSectors();
		this.sector = new Sector();
	}

	public void createSector() {
		System.out.println("Save");
		System.out.println(this.getSectorName());
	}

	public void setSectorService(SectorService sectorService) {
		this.sectorService = sectorService;
	}

}
