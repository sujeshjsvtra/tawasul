package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.service.ConsultationService;
import org.apache.commons.lang3.StringUtils;

import com.tawasul.web.model.Sector;
import com.tawasul.web.service.SectorService;

import lombok.*;


@RequestScoped
@Named
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Consultation> consultations;
	private Consultation consultation;

	//Input fields
	private String consultationName;
	private String consultationTopic;
	private String consultationDescription;
	private String sectorName;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<Sector> sectorList;
	private Map<String, Long> sectorDropdown;
	private String selectedSector;

	@Inject
	private ConsultationService consultationService;

	@Inject
	private SectorService sectorService;

	@PostConstruct
	public void init() {

		resetConsultationForm();
		consultationService = new ConsultationService();
		setConsultations(consultationService.populateConsultations());

		sectorService = new SectorService();
		setSectorList(sectorService.populateSectors());
		sectorDropdown = getSectorList().stream().collect(Collectors.toMap(Sector::getName, Sector::getId));
	}

	public void resetConsultationForm() {
		setConsultationName("");
		setConsultationTopic("");
		setConsultationDescription("");
		setStartDate(LocalDate.now());
		setEndDate(LocalDate.now());
		setSelectedSector("");
	}


	@PreDestroy
	public void preDestory() {
	}

	public void saveConsultation() {
		if (StringUtils.isNotBlank(this.getConsultationName())) {
			consultationService.saveConsultation(getConsultationName(), getConsultationTopic(),
					getConsultationDescription(), getStartDate(), getEndDate(), this.fetchSector(), "A");

			resetConsultationForm();
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved successfully");
		} else {
			//new FacesMessage(FacesMessage.SEVERITY_WARN, "Failure", "Sector Name is required");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sector Name is required", "Sector Name is required"));
		}
	}
	
	private Sector fetchSector() {
		Sector filteredSector = getSectorList().stream()
				.filter(sector -> getSelectedSector().equals(String.valueOf(sector.getId())))
				.findFirst().orElse(null);
		return filteredSector;
	}
}

