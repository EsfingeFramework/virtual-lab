package org.esfinge.virtuallab.demo.satelite;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "satelitetelemetry")
public class PollsSatelitetelemetry{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "altitude_gps")
    private Double altitudeGps;
    
    
    
    public PollsSatelitetelemetry(Integer id, Double altitudeGps) {
		this.id = id;
		this.altitudeGps = altitudeGps;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Double getAltitudeGps() {
		return altitudeGps;
	}

	public void setAltitudeGps(Double altitudeGps) {
		this.altitudeGps = altitudeGps;
	}

    
}
