package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.Sector;
import com.tawasul.web.service.ConsultationService;
import com.tawasul.web.service.SectorService;
import com.tawasul.web.util.SystemConstants;

import lombok.*;


@ApplicationScoped
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
	private Consultation existingConsultation;

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

	@Inject
	private PageRedirect pageRedirect;

	@PostConstruct
	public void init() {
		System.out.println("Post construct called: " + LocalDateTime.now());

		pageRedirect = new PageRedirect();
		resetConsultationForm();
		consultationService = new ConsultationService();

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String parameterId = request.getParameter("id");
		if (StringUtils.isNotBlank(parameterId)) {
			existingConsultation = new Consultation();
			setExistingConsultation(loadExistingConsultation(parameterId));
		}

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

			System.out.println("Existing consultation : " +this.getExistingConsultation());
			consultationService.saveOrUpdateConsultation(this.getExistingConsultation(), getConsultationName(), getConsultationTopic(),
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
		return getSectorList().stream()
				.filter(sector -> getSelectedSector().equals(String.valueOf(sector.getId())))
				.findFirst().orElse(null);
	}

	// View / Edit / Delete Consultation
	public String fetchSectorName(Consultation consultation) {
		if (consultation != null && consultation.getSector() != null) {
			return consultation.getSector().getName();
		}
		{
			return "";
		}

	}

	public Consultation loadExistingConsultation(String id) {
		consultation = consultationService.fetchConsultationById(Long.parseLong(id));
		System.out.println("Fetched : "+ consultation.toString());

		setConsultationName(consultation.getName());
		setConsultationTopic(consultation.getTopic());
		setConsultationDescription(consultation.getDescription());
		setStartDate(consultation.getStartDate());
		setEndDate(consultation.getEndDate());
		setSelectedSector(consultation.getSector().getId().toString());

		return consultation;
	}


	public void editConsultation(Consultation consultation) {
		System.out.println("Edit Consultation: "  + consultation);
		pageRedirect.redirectToPage(SystemConstants.EDIT_CONSULTATIONS_SCREEN + "?id=" + consultation.getId());
	}

	
	public void deleteConsultation(Consultation consultation) {
		consultationService.deleteConsultation(consultation);
	}

}

