package com.tawasul.web.service.impl;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.Sector;
import com.tawasul.web.service.UserViewService;
import com.tawasul.web.util.DBUtil;

@ManagedBean(name = "userViewServiceImpl")
@SessionScoped
public class UserViewServiceImpl implements UserViewService {

	@Override
	public List<Consultation> getAllConsultations() {
		try {
			List<Consultation> consultations = DBUtil.getAllActiveConsultations();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Sector> getAllSectors() {
		try {
			List<Sector> sectors = DBUtil.getAllSectors();
			return sectors;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Consultation> getAllOpenConsultations() {
		try {
			List<Consultation> consultations = DBUtil.getAllOpenConsultations();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Consultation> getAllClosedConsultations() {
		try {
			List<Consultation> consultations = DBUtil.getAllClosedConsultations();
			return consultations;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Consultation> getAllConsultationsByRangeOrSector(Integer type, Long sectorId, List<Date> range) {
		try {
			if (range != null && range.size() > 1)
				return DBUtil.getAllConsultationByFilter(type, sectorId, range.get(0), range.get(1));
			return DBUtil.getAllConsultationByFilter(type, sectorId, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
