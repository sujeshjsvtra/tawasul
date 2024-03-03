package com.tawasul.web.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.tawasul.web.model.Sector;
import com.tawasul.web.service.SectorService;
import com.tawasul.web.util.MessageUtil;
import com.tawasul.web.util.StatusEnum;

import lombok.*;

@ViewScoped
@Named
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SectorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Sector> sectors;
	private Sector sector;
	private String sectorName;
	private String sectorNameArabic;
	private String status;
	private List<Sector> selectedSectors;
	private Map<String, String> statusMap;
	private Sector existingSector;
	private boolean editMode;

	//@ManagedProperty(value="#{sectorService}")
	@Inject
	private SectorService sectorService;

	@PostConstruct
	public void init() {
		System.out.println("Post construct called at " + LocalDateTime.now());
		sectorService = new SectorService();

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String parameterId = request.getParameter("id");
		if (StringUtils.isNotBlank(parameterId)) {
			existingSector = new Sector();
			setExistingSector(loadExistingSector(parameterId));
			setEditMode(true);
		}
		setSectors(sectorService.populateSectors());

		statusMap = Arrays.stream(StatusEnum.values())
				.collect(Collectors.toMap(StatusEnum::name, StatusEnum::getStatus));
		setStatus("O");
	}

	@PreDestroy
	public void preDestory() {
	}

	public void saveOrUpdateSector() {
		if (StringUtils.isNotBlank(this.getSectorName()) || StringUtils.isNotBlank(this.getSectorNameArabic())) {
			sectorService.saveOrUpdateSector(existingSector, this.getSectorName(), this.getSectorNameArabic(),
					this.getStatus());
			resetSectorForm();
			if (editMode) {
				MessageUtil.info("Updated successfully");
			} else {
				MessageUtil.info("Saved successfully");
			}

		} else {
			MessageUtil.error("Sector Name is required");
		}
	}

	public void resetSectorForm() {
		setSectorName("");
		setSectorNameArabic("");
	}

	public String editSector(Sector sector) {
		return "add-sector?id=" + sector.getId() + "faces-redirect=true";
	}

	public void deleteSector(Sector sector) {
		sectorService.deleteSector(sector);
	}

	public Sector loadExistingSector(String id) {
		sector = sectorService.fetchSectorById(Long.parseLong(id));
		System.out.println("Fetched sector: " + sector.toString());

		setSectorName(sector.getName());
		setSectorNameArabic(sector.getArabicName());
		setStatus(sector.getStatus());

		return sector;
	}
}
