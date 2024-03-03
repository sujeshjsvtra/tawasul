package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.Sector;
import com.tawasul.web.service.ConsultationService;
import com.tawasul.web.service.SectorService;
import com.tawasul.web.util.MessageUtil;
import com.tawasul.web.util.StatusEnum;
import com.tawasul.web.util.SystemConstants;

import lombok.*;

@ViewScoped
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
	private boolean editMode;

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
	private String status;

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
			setEditMode(true);
		}

		setConsultations(consultationService.populateConsultations());

		sectorService = new SectorService();
		setSectorList(sectorService.populateSectors());
		setStatus(StatusEnum.OPEN.getStatus());
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

	public String saveOrUpdateConsultation() {
		if (StringUtils.isNotBlank(this.getConsultationName())) {

			System.out.println("Existing consultation : " +this.getExistingConsultation());
			consultationService.saveOrUpdateConsultation(this.getExistingConsultation(), getConsultationName(), getConsultationTopic(),
					getConsultationDescription(), getStartDate(), getEndDate(), this.fetchSector(), this.getStatus());

			resetConsultationForm();
			if (editMode) {
				MessageUtil.info("Updated successfully");
				return "view-consultation" + "?faces-redirect=true";
			} else {
				MessageUtil.info("Saved successfully");
			}
			MessageUtil.info("Saved successfully");
		} else {
			MessageUtil.error("Error");
		}
		return "";
	}

	private Sector fetchSector() {
		return getSectorList().stream()
				.filter(sector -> getSelectedSector().equals(String.valueOf(sector.getId())))
				.findFirst().orElse(null);
	}

	public String convertDate(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return localDate.format(formatter);
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

	public String fetchStatus(Consultation consultation) {
		if (consultation != null && consultation.getStatus() != null) {
			return StatusEnum.getDisplayNameByStatus(consultation.getStatus());
		}
		{
			return "Invalid";
		}
	}

	public Consultation loadExistingConsultation(String id) {
		consultation = consultationService.fetchConsultationById(Long.parseLong(id));
		//System.out.println("Fetched : "+ consultation.toString());

		setConsultationName(consultation.getName());
		setConsultationTopic(consultation.getTopic());
		setConsultationDescription(consultation.getDescription());
		setStartDate(consultation.getStartDate());
		setEndDate(consultation.getEndDate());
		setSelectedSector(consultation.getSector().getId().toString());

		return consultation;
	}

	@Transactional
	public void editConsultation(Consultation consultation) {
 			System.out.println("Edit Consultation: "  + consultation);
		pageRedirect.redirectToPage(SystemConstants.ADD_CONSULTATIONS_SCREEN + "?id=" + consultation.getId());
	}

	@Transactional
	public void deleteConsultation(Consultation consultation) {
		consultationService.deleteConsultation(consultation);
		MessageUtil.info("Deleted successfully");
	}

}

