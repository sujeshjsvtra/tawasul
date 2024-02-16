package com.tawasul.web.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
//JSF
//import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import com.tawasul.web.model.Sector;

//JSF
//@ApplicationScoped
@Named
@ApplicationScoped
@Getter
@Setter
public class SectorService {

	private List<Sector> sectors;

	@PostConstruct
	public void init() {
		System.out.println("PostConstruct SectorService: " + LocalDateTime.now());
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

		/*
		 * // make sure to have unique codes for (Product product : results) {
		 * product.setCode(UUID.randomUUID().toString().replace("-", "").substring(0,
		 * 8)); }
		 */
		return sectorsList;
	}
}
