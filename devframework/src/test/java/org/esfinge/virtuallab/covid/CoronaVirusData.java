package org.esfinge.virtuallab.covid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Calendar;



@Entity
public class CoronaVirusData{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Calendar data;

	private String location;

	private int newCases;

	private int newDeaths;

	private int totalCases;

	private int totalDeaths;

	public CoronaVirusData() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return this.data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNewCases() {
		return this.newCases;
	}

	public void setNewCases(Integer newCases) {
		this.newCases = newCases;
	}

	public int getNewDeaths() {
		return this.newDeaths;
	}

	public void setNewDeaths(int newDeaths) {
		this.newDeaths = newDeaths;
	}

	public int getTotalCases() {
		return this.totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getTotalDeaths() {
		return this.totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

}