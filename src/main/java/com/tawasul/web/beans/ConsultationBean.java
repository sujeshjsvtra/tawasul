package com.tawasul.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
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

	private String consultationNameArabic;
	private String consultationTopicArabic;
	private String consultationDescriptionArabic;

	private boolean englishFieldsRequired;
	private boolean arabicFieldsRequired;

	private String sectorName;
	private Date startDate;
	private Date endDate;
	private List<Sector> sectorList;
	private Map<String, Long> sectorDropdown;
	private String selectedSector;
	private String status;
	private UploadedFile uploadedFile;
	private String imageUrl;
	private File existingFile;
	private String placeholderImage;
	private List<Date> dateRange;
	private List<Date> dateRangeToFilterConsultations;
	//Current Date
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
		setDateRange(new ArrayList<>());
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
				.collect(Collectors.toMap(sector -> sector.getName() + " | " + sector.getArabicName(), Sector::getId));
	}

	public void handleLanguageFieldsValidation() {
		if (getConsultationName().isEmpty() && getConsultationTopic().isEmpty() && getConsultationDescription().isEmpty())
		{
			setArabicFieldsRequired(true);
		}
		if (getConsultationNameArabic().isEmpty() && getConsultationTopicArabic().isEmpty() && getConsultationDescriptionArabic().isEmpty())
		{
			setEnglishFieldsRequired(true);
		}

		System.out.println("English fields: " +isEnglishFieldsRequired());
		System.out.println("Arabic fields: " +isArabicFieldsRequired());
	}

	public void filterBySectors() {
		List<String> sectorsList = Arrays.stream(getSectorsToFilterConsultations()).distinct()
				.collect(Collectors.toList());

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

	public void filterByDateRange() {
		System.out.println("Entered Date Range " + getDateRangeToFilterConsultations());

		if (getDateRangeToFilterConsultations() != null) {
			Date startDate = getDateRangeToFilterConsultations().get(0);
			Date endDate = getDateRangeToFilterConsultations().get(1);

			List<Consultation> filteredConsultations = consultations.stream()
					.filter(consultation -> isWithinDateRange(consultation, startDate, endDate))
					.collect(Collectors.toList());
			setConsultations(filteredConsultations);
			System.out.println("Filtered consultations : " + filteredConsultations.size());

		} else {
			setConsultations(consultationService.populateConsultations());
		}

	}

	private boolean isWithinDateRange(Consultation consultation, Date startDate, Date endDate) {
		Date consultationStartDate = consultation.getStartDate();
		Date consultationEndDate = consultation.getEndDate();

		return consultationStartDate != null && consultationEndDate != null &&
				!consultationEndDate.before(startDate) && !consultationStartDate.after(endDate);
	}


	public void resetConsultationForm() {
		setConsultationName("");
		setConsultationTopic("");
		setConsultationDescription("");
		setConsultationNameArabic("");
		setConsultationTopicArabic("");
		setConsultationDescriptionArabic("");
		setDateRange(new ArrayList<>());
		setSelectedSector("");
		setEnglishFieldsRequired(true);
		setArabicFieldsRequired(true);
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
		handleLanguageFieldsValidation();
		if (StringUtils.isNotBlank(this.getConsultationName())) {
			File imageToSave = saveFile();

			consultationService.saveOrUpdateConsultation(this.getExistingConsultation(), getConsultationName(),
					getConsultationTopic(), getConsultationDescription(), this.getConsultationNameArabic(),
					this.getConsultationTopicArabic(), this.getConsultationDescriptionArabic(), getDateRange().get(0),
					getDateRange().get(1), this.fetchSector(), imageToSave, this.getStatus());

			resetConsultationForm();
			if (editMode) {
				MessageUtil.info("Updated successfully");
				return "view-consultations" + "?faces-redirect=true";
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
		setConsultationNameArabic(consultation.getNameArabic());
		setConsultationTopicArabic(consultation.getTopicArabic());
		setConsultationDescriptionArabic(consultation.getDescriptionArabic());
		setDateRange(Arrays.asList(consultation.getStartDate(), consultation.getEndDate()));
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
