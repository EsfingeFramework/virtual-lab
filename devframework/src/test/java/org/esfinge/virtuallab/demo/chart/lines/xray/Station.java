package org.esfinge.virtuallab.demo.chart.lines.xray;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="station", schema = "goes")
public class Station {
	
	@Id
	private Long id;
	
	private String code;
	
	@Column(name = "name_station")
	private String nameStation;
	
	@Column(name = "location")
	private  String location;
	
	@Column(name = "last_point_embrace")
	private Calendar lastPointEmbrace;
	
	@Column(name = "last_point_noaa")
	private  Calendar LastPointNoaa;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Calendar getLastPointEmbrace() {
		return lastPointEmbrace;
	}

	public void setLastPointEmbrace(Calendar lastPointEmbrace) {
		this.lastPointEmbrace = lastPointEmbrace;
	}

	public Calendar getLastPointNoaa() {
		return LastPointNoaa;
	}

	public void setLastPointNoaa(Calendar lastPointNoaa) {
		LastPointNoaa = lastPointNoaa;
	}

	public String getNameStation() {
		return nameStation;
	}

	public void setNameStation(String nameStation) {
		this.nameStation = nameStation;
	}
	
	

}
