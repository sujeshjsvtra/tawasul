package com.tawasul.web.beans;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.collections4.MapUtils;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.Sector;
import com.tawasul.web.service.impl.UserViewServiceImpl;
import com.tawasul.web.util.MessageUtil;

@ManagedBean(name = "userConsultationBean")
@SessionScoped
public class UserConsultationBean {

	private List<Consultation> consultations;
	private List<Sector> sectors;
	private Map<String, String> sectorMaps = new HashMap<>();
	private List<Date> range;
	private Long sectorId;
	private String sectorName;
	private Sector sector;
	private Integer type ;

	@ManagedProperty(value = "#{userViewServiceImpl}")
	private UserViewServiceImpl userViewServiceImpl;

	@PostConstruct
	public void init() {
		consultations = new ArrayList<>();
		sectors = new ArrayList<>();
		sectorMaps = new HashMap<>();
		range = new ArrayList<>();
		type= 2;
		fetchSectors();
		fetchConsultations();
	}

	public void remoteCallForConsultations() {
		System.out.println(" Remote Command");
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		type = (Integer) map.get("type");
		if (type == 2)
			fetchConsultations();
		if (type == 1)
			allOpenConsultations();
		if (type == 0)
			allClosedConsultations();

	}

	public void sampleTest() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map map = context.getExternalContext().getRequestParameterMap();
			type = Integer.parseInt((String) map.get("type"));
			if (type == 2)
				fetchConsultations();
			if (type == 1)
				allOpenConsultations();
			if (type == 0)
				allClosedConsultations();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public  String formatDate(LocalDate date) {
	        String month = date.getMonth().toString();
	        int dayOfMonth = date.getDayOfMonth();
	        int year = date.getYear();
	        
	        // Determine the suffix for the day of the month
	        String suffix;
	        if (dayOfMonth >= 11 && dayOfMonth <= 13) {
	            suffix = "th";
	        } else {
	            switch (dayOfMonth % 10) {
	                case 1:  suffix = "st"; break;
	                case 2:  suffix = "nd"; break;
	                case 3:  suffix = "rd"; break;
	                default: suffix = "th"; break;
	            }
	        }
	        month = month.toString().substring(0, 1).toUpperCase() + month.toString().substring(1).toLowerCase();
	        // Format the date
	        return String.format("%s %d%s, %d", month, dayOfMonth, suffix, year);
	    }

	public void filterSeacrhConsultation() {
		setConsultations(userViewServiceImpl.getAllConsultationsByRangeOrSector(type, getSectorId(), getRange()));
	}
	
	public void redirectToSureveyPage() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Integer id =  MapUtils.getIntValue(map, "id");
		System.out.println(id);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.getFlash().put("consultationId", id); // Using Flash scope to pass parameter
        try {
        	externalContext.redirect("survey.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void fetchConsultations() {
		setConsultations(userViewServiceImpl.getAllConsultations());
	}

	public void fetchSectors() {
		setSectors(userViewServiceImpl.getAllSectors());
		sectorMaps = new HashMap<>();
		for (Sector secotr : getSectors()) {
			sectorMaps.put(secotr.getName(), secotr.getId() + "");
		}
	}

	public void allOpenConsultations() {
		setConsultations(userViewServiceImpl.getAllOpenConsultations());
	}

	public void allClosedConsultations() {
		setConsultations(userViewServiceImpl.getAllClosedConsultations());
	}

	public List<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(List<Consultation> consultations) {
		this.consultations = consultations;
	}

	public List<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public UserViewServiceImpl getUserViewServiceImpl() {
		return userViewServiceImpl;
	}

	public void setUserViewServiceImpl(UserViewServiceImpl userViewServiceImpl) {
		this.userViewServiceImpl = userViewServiceImpl;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public Map<String, String> getSectorMaps() {
		return sectorMaps;
	}

	public void setSectorMaps(Map<String, String> sectorMaps) {
		this.sectorMaps = sectorMaps;
	}

	public List<Date> getRange() {
		return range;
	}

	public void setRange(List<Date> range) {
		this.range = range;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

}
