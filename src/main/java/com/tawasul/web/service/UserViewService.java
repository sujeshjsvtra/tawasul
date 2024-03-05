package com.tawasul.web.service;

import java.util.Date;
import java.util.List;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.Sector;
 

public interface UserViewService {

	public List<Consultation> getAllConsultations();

	public List<Sector> getAllSectors();

	public List<Consultation> getAllOpenConsultations();
	
	public List<Consultation> getAllClosedConsultations();
	
	public List<Consultation> getAllConsultationsByRangeOrSector(Integer type,Long secorId,List<Date> range);

}
