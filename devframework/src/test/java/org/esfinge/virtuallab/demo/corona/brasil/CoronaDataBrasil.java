package org.esfinge.virtuallab.demo.corona.brasil;

import java.util.Calendar;

public class CoronaDataBrasil {
	private String regiao;
	private String estado;
	private Calendar data;
	private int casosNovos;
	private int casosAcumulados;
	private int obitosNovos;
	private int obitosAcumulados;
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public int getCasosNovos() {
		return casosNovos;
	}
	public void setCasosNovos(int casosNovos) {
		this.casosNovos = casosNovos;
	}
	public int getCasosAcumulados() {
		return casosAcumulados;
	}
	public void setCasosAcumulados(int casosAcumulados) {
		this.casosAcumulados = casosAcumulados;
	}
	public int getObitosNovos() {
		return obitosNovos;
	}
	public void setObitosNovos(int obitosNovos) {
		this.obitosNovos = obitosNovos;
	}
	public int getObitosAcumulados() {
		return obitosAcumulados;
	}
	public void setObitosAcumulados(int obitosAcumulados) {
		this.obitosAcumulados = obitosAcumulados;
	}
	
	@Override
	public String toString() {
		return "CoronaDataBrasil [regiao=" + regiao + ", estado=" + estado + ", data=" + data + ", casosNovos="
				+ casosNovos + ", casosAcumulados=" + casosAcumulados + ", obitosNovos=" + obitosNovos
				+ ", obitosAcumulados=" + obitosAcumulados + "]";
	}
	
	
	
}
