package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
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
		this.sector = new Sector();
	}

	public void createSector() {
		System.out.println(this.getSectorName());
	}
}
