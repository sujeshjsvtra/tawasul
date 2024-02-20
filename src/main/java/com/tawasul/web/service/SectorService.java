package com.tawasul.web.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import com.tawasul.web.model.Sector;

@Named
@SessionScoped
@Getter
@Setter
public class SectorService implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sector> sectors;

	@PostConstruct
	public void init() {
		setSectors(getSectors());
	}

	public List<Sector> populateSectors() {

		Sector s1 = new Sector();
		s1.setId(1L);
		s1.setName("Finance");
		s1.setStatus("active");

		Sector s2 = new Sector();
		s2.setId(2L);
		s2.setName("Health");
		s2.setStatus("active");

		List<Sector> sectorsList = new ArrayList<>();
		sectorsList.add(s1);
		sectorsList.add(s2);

		return sectorsList;
	}
}
