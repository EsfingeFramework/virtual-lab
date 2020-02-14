package org.esfinge.virtuallab.demo.chart.lines.xray;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.esfinge.querybuilder.annotation.Condition;
import net.sf.esfinge.querybuilder.annotation.DomainTerm;

/**
 * The persistent class for the xray_data_low database table.
 * 
 */
@Entity
@Table(name="xray_data_low", schema = "goes")
public class XrayDataLow{

	@Id
	private Long id;

	@Column(name="event_datetime")
	private Calendar eventDateTime;

	@Column(name="long_xray")
	private double longXray;

	private double  ratio;

	@Column(name="short_xray")
	private double  shortXray;

	
	@Column(name="header_fk")
	private Long header;

	@Transient
	private double R1 = 0.00001; 
	
	@Transient
	private double R2 = 0.00005;
	
	@Transient
	private double R3 =  0.0001;
	
	@Transient
	private double R4 = 0.001;
	
	@Transient
	private double R5 = 0.002;
	
	
	public XrayDataLow() {
	}
	
	
	

	public double getShortXray() {
		return shortXray;
	}



	public void setShortXray(double shortXray) {
		this.shortXray = shortXray;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Calendar getEventDateTime() {
		return eventDateTime;
	}



	public void setEventDateTime(Calendar eventDateTime) {
		this.eventDateTime = eventDateTime;
	}



	public double getLongXray() {
		return longXray;
	}



	public void setLongXray(double longXray) {
		this.longXray = longXray;
	}



	public double getRatio() {
		return ratio;
	}



	public void setRatio(double ratio) {
		this.ratio = ratio;
	}



	public Long getHeader() {
		return header;
	}



	public void setHeader(Long header) {
		this.header = header;
	}



	public double getR1() {
		return R1;
	}



	public void setR1(double r1) {
		R1 = r1;
	}



	public double getR2() {
		return R2;
	}



	public void setR2(double r2) {
		R2 = r2;
	}



	public double getR3() {
		return R3;
	}



	public void setR3(double r3) {
		R3 = r3;
	}



	public double getR4() {
		return R4;
	}



	public void setR4(double r4) {
		R4 = r4;
	}



	public double getR5() {
		return R5;
	}



	public void setR5(double r5) {
		R5 = r5;
	}



	@Override
	public String toString() {
		return "XrayDataLow [id=" + id + ", eventDateTime=" + eventDateTime + ", longXray=" + longXray + ", ratio="
				+ ratio + ", shortXray=" + shortXray + ", header=" + header + ", R1=" + R1 + ", R2=" + R2 + ", R3=" + R3
				+ ", R4=" + R4 + ", R5=" + R5 + "]";
	}

	
	
	
	

}