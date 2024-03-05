package com.tawasul.web.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
		String propertyValue = resourceBundle.getString("file-size-limit");
		setFileSizeInBytes(Long.parseLong(propertyValue));

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
			setImageUrl(existingConsultation.getImage().getUrl());
			setExistingFile(existingConsultation.getImage());
		}

		setConsultations(consultationService.populateConsultations());

		sectorService = new SectorService();
		setSectorList(sectorService.populateSectors());
		setStatus(StatusEnum.OPEN.getStatus());
		sectorDropdown = getSectorList().stream()
				.collect(Collectors.toMap(sector -> sector.getName() + " | " + sector.getArabicName(), Sector::getId));
	}

	@PreDestroy
	public void preDestory() {
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
		pageRedirect.redirectToPage(SystemConstants.ADD_CONSULTATIONS_SCREEN + "?id=" + consultation.getId());
	}

	@Transactional
	public void deleteConsultation(Consultation consultation) {
		consultationService.deleteConsultation(consultation);
		MessageUtil.info("Deleted successfully");
	}
}
