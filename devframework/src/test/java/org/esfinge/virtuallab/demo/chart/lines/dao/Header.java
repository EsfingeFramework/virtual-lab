package org.esfinge.virtuallab.demo.chart.lines.dao;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="header", schema = "goes")
public class Header {
	
	@Id
	private Long id;

	
	@Column(name = "acquisition_fk")
	private long aquisition;
	
	
	@Column(name="station_equipment_fk")
	private long equipament;
	
	@Column(name = "time_resolution")
	private long timeResolution;
	
	@Column(name = "event_date")
	private Calendar eventDate;
	
	@Column(name = "time_start")
	private Calendar timeStart;
	
	@Column(name = "time_end")
	private Calendar timeEnd;
	
	@Column(name = "missing_data")
	private double missingData;

	public Header()
	{
		
	}
	
	
	
	public Header(Long id, long aquisition, long equipament, long timeResolution, Calendar eventDate,
			Calendar timeStart, Calendar timeEnd, double missingData) {
		this.id = id;
		this.aquisition = aquisition;
		this.equipament = equipament;
		this.timeResolution = timeResolution;
		this.eventDate = eventDate;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.missingData = missingData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAquisition() {
		return aquisition;
	}

	public void setAquisition(long aquisition) {
		this.aquisition = aquisition;
	}

	public long getEquipament() {
		return equipament;
	}

	public void setEquipament(long equipament) {
		this.equipament = equipament;
	}

	public long getTimeResolution() {
		return timeResolution;
	}

	public void setTimeResolution(long timeResolution) {
		this.timeResolution = timeResolution;
	}

	public Calendar getEventDate() {
		return eventDate;
	}

	public void setEventDate(Calendar eventDate) {
		this.eventDate = eventDate;
	}

	public Calendar getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Calendar timeStart) {
		this.timeStart = timeStart;
	}

	public Calendar getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Calendar timeEnd) {
		this.timeEnd = timeEnd;
	}

	public double getMissingData() {
		return missingData;
	}

	public void setMissingData(double missingData) {
		this.missingData = missingData;
	}
	
	
}
