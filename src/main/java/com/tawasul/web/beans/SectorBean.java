package com.tawasul.web.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

	@Inject
	private SectorService sectorService;

	@PostConstruct
	public void init() {
		sectorService = new SectorService();

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String parameterId = request.getParameter("id");
		if (StringUtils.isNotBlank(parameterId)) {
			existingSector = new Sector();
			setExistingSector(loadExistingSector(parameterId));
		}
		setSectors(sectorService.populateSectors());

		statusMap =  Arrays.stream(StatusEnum.values())
				.collect(Collectors.toMap(StatusEnum::name, StatusEnum::getStatus));
	}

	@PreDestroy
	public void preDestory() {
	}

	public void saveSector() {
		if (StringUtils.isNotBlank(this.getSectorName())) {
			sectorService.saveSector(this.getSectorName(), this.getSectorNameArabic(), "O");
			resetSectorForm();
			MessageUtil.info("Saved successfully");
		} else {
			MessageUtil.error("Sector Name is required");
		}
	}

	public void resetSectorForm() {
		setSectorName("");
		setSectorNameArabic("");
	}

	public String editSector(Sector sector) {
		return "edit-sector?id="+sector.getId()+"faces-redirect=true";
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
