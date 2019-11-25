package org.esfinge.virtuallab.demo.chart.lines.xray;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="station_equipment", schema = "goes")
public class StationEquipament {

	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "station_fk")
	private Station stationFk;
	
	private Long equipment_fk;
	
	private Long priority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Station getStationFk() {
		return stationFk;
	}

	public void setStationFk(Station stationFk) {
		this.stationFk = stationFk;
	}

	public Long getEquipment_fk() {
		return equipment_fk;
	}

	public void setEquipment_fk(Long equipment_fk) {
		this.equipment_fk = equipment_fk;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}
	
	
	
}
