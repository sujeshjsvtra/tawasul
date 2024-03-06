package com.tawasul.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import com.tawasul.web.model.Consultation;
import com.tawasul.web.model.File;
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

	// Input fields
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
	private UploadedFile uploadedFile;
	private String imageUrl;
	private File existingFile;
	private String placeholderImage;
	private Date today;
	private String[] sectorsToFilterConsultations;

	ResourceBundle resourceBundle = ResourceBundle.getBundle("application-local");
	private Long fileSizeInBytes;

	@Inject
	private ConsultationService consultationService;

	@Inject
	private SectorService sectorService;

	@Inject
	private PageRedirect pageRedirect;

	@PostConstruct
	public void init() {
		System.out.println("Consultation Bean Post construct called: " + LocalDateTime.now());
		setToday(new Date());
		String propertyValue = resourceBundle.getString("file-size-limit");
		setFileSizeInBytes(Long.parseLong(propertyValue));
		setPlaceholderImage(resourceBundle.getString("placeholder-image"));
		

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

			if (existingConsultation.getImage() != null) {
				setImageUrl(existingConsultation.getImage().getUrl());
			} else {
				setImageUrl(getPlaceholderImage());
			}
			setExistingFile(existingConsultation.getImage());
		}

		setConsultations(consultationService.populateConsultations());

		sectorService = new SectorService();
		setSectorList(sectorService.populateSectors());
		setStatus(StatusEnum.OPEN.getStatus());
		sectorDropdown = getSectorList().stream()
				//
				.collect(Collectors.toMap(sector -> sector.getName(), Sector::getId));
	}


	public void selectSectors() {
		List<String> sectorsList = Arrays.stream(getSectorsToFilterConsultations()).distinct()
				.collect(Collectors.toList());

		System.out.println("Distinct sectors  " + sectorsList);

		if (!sectorsList.isEmpty()) {
			List<Consultation> filteredConsultations = getConsultations().stream()
					.filter(consultation -> sectorsList.contains(String.valueOf(consultation.getSector().getId())))
					.collect(Collectors.toList());
			setConsultations(filteredConsultations);
			System.out.println("Filtered consultations: " + filteredConsultations);
		} else {
			setConsultations(consultationService.populateConsultations());
		}
	}

	public void resetConsultationForm() {
		setConsultationName("");
		setConsultationTopic("");
		setConsultationDescription("");
		setStartDate(LocalDate.now());
		setEndDate(LocalDate.now());
		setSelectedSector("");
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		MessageUtil.info("File uploaded");
		setUploadedFile(event.getFile());
	}

	public File saveFile() {
		File fileToSave = null;

		if (getUploadedFile() != null) {
			String fileName = getUploadedFile().getFileName();
			String baseName = FilenameUtils.getBaseName(getUploadedFile().getFileName());
			String extension = FilenameUtils.getExtension(getUploadedFile().getFileName());

			if (getExistingFile() != null) {
				fileToSave = getExistingFile();
			} else {
				fileToSave = new File();
			}

			fileToSave.setName(baseName);
			fileToSave.setType(extension);
			fileToSave.setStatus("A");
			fileToSave.setUrl(fileName);
			fileToSave.setModule(SystemConstants.CONSULTATION_MODULE_NAME);
			consultationService.saveOrUpdateFile(fileToSave);

		}
		return fileToSave;
	}

	public String saveOrUpdateConsultation() {
		if (StringUtils.isNotBlank(this.getConsultationName())) {
			File imageToSave = saveFile();

			consultationService.saveOrUpdateConsultation(this.getExistingConsultation(), getConsultationName(),
					getConsultationTopic(), getConsultationDescription(), getStartDate(), getEndDate(),
					this.fetchSector(), imageToSave, this.getStatus());

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
		return getSectorList().stream().filter(sector -> getSelectedSector().equals(String.valueOf(sector.getId())))
				.findFirst().orElse(null);
	}

	public String convertDate(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if(localDate!=null) {
			return localDate.format(formatter);
		}
		return "";
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

	public boolean isOpen(Consultation consultation) {
		boolean isOpen = false;
		if (consultation != null && consultation.getStatus() != null) {
			isOpen = consultation.getStatus().equals(StatusEnum.OPEN.getStatus()) ? true : false;
		}
		return isOpen;
	}


	public Consultation loadExistingConsultation(String id) {
		consultation = consultationService.fetchConsultationById(Long.parseLong(id));

		setConsultationName(consultation.getName());
		setConsultationTopic(consultation.getTopic());
		setConsultationDescription(consultation.getDescription());
		setStartDate(consultation.getStartDate());
		setEndDate(consultation.getEndDate());
		setSelectedSector(consultation.getSector().getId().toString());

		return consultation;
	}

	public void validateDates() {
		System.out.println("Date Validation");
		System.out.println("start date: "+getStartDate());
		System.out.println("end date : "+getEndDate());

		if (getStartDate() != null && getEndDate() != null && getStartDate().isAfter(getEndDate()) && getEndDate().isBefore(getStartDate()))  {
			MessageUtil.error("Start Date must be before End Date");
			setStartDate(LocalDate.now());
			setEndDate(LocalDate.now());
		}
	}

	@Transactional
	public void editConsultation(Consultation consultation) {
		pageRedirect.redirectToPage(SystemConstants.ADD_CONSULTATIONS_SCREEN + "?id=" + consultation.getId());
	}

	@Transactional
	public void deleteConsultation(Consultation consultation) {
		consultationService.deleteConsultation(consultation);
		MessageUtil.info("Deleted successfully");
	}
}
