package com.tawasul.web.resource;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.tawasul.web.service.Car;
import com.tawasul.web.service.CarService;

@ManagedBean(name = "adminDashboardView")
@ViewScoped
public class AdminDashboardView {
	private List<Car> cars;
    
     
    private CarService service;
 
    @PostConstruct
    public void init() {
    	service = new CarService();
        cars = service.createCars(10);
    }
     
    public List<Car> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
}
