package org.esfinge.virtuallab.demo.satelite;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "polls_sat")
public class PollsSatelitetelemetry{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sync")
    private String sync;
    @Basic(optional = false)
    @Column(name = "contador_mensagens")
    private int contadorMensagens;
    @Basic(optional = false)
    @Column(name = "nro_pacote1")
    private int nroPacote1;
    @Basic(optional = false)
    @Column(name = "nro_pacote2")
    private int nroPacote2;
    @Basic(optional = false)
    @Column(name = "nro_pacote3")
    private int nroPacote3;
    @Basic(optional = false)
    @Column(name = "latitude_gps")
    private double latitudeGps;
    @Basic(optional = false)
    @Column(name = "longitude_gps")
    private double longitudeGps;
    @Basic(optional = false)
    @Column(name = "dia")
    private int dia;
    @Basic(optional = false)
    @Column(name = "mes")
    private int mes;
    @Basic(optional = false)
    @Column(name = "ano")
    private int ano;
    @Basic(optional = false)
    @Column(name = "hora")
    private int hora;
    @Basic(optional = false)
    @Column(name = "minuto")
    private int minuto;
    @Basic(optional = false)
    @Column(name = "segundo")
    private int segundo;
    @Basic(optional = false)
    @Column(name = "hdop")
    private int hdop;
    @Basic(optional = false)
    @Column(name = "nro_satelite")
    private int nroSatelite;
    @Basic(optional = false)
    @Column(name = "speed")
    private double speed;
    @Basic(optional = false)
    @Column(name = "curso")
    private double curso;
    @Basic(optional = false)
    @Column(name = "altitude_gps")
    private double altitudeGps;
    @Basic(optional = false)
    @Column(name = "pot_rec_bal")
    private double potRecBal;
    @Basic(optional = false)
    @Column(name = "temp_inter_um")
    private double tempInterUm;
    @Basic(optional = false)
    @Column(name = "temp_ext")
    private double tempExt;
    @Basic(optional = false)
    @Column(name = "temp_inter_dois")
    private double tempInterDois;
    @Basic(optional = false)
    @Column(name = "pressao")
    private double pressao;
    @Basic(optional = false)
    @Column(name = "pressao_mar")
    private double pressaoMar;
    @Basic(optional = false)
    @Column(name = "altitude_BMP")
    private int altitudeBMP;
    @Basic(optional = false)
    @Column(name = "pot_tx_no_balao")
    private double potTxNoBalao;
    @Basic(optional = false)
    @Column(name = "status_header")
    private int statusHeader;
    @Basic(optional = false)
    @Column(name = "status_temperatura")
    private int statusTemperatura;
    @Basic(optional = false)
    @Column(name = "ds18b20")
    private int ds18b20;
    @Basic(optional = false)
    @Column(name = "bmp180")
    private int bmp180;
    @Basic(optional = false)
    @Column(name = "sdcard")
    private int sdcard;
    @Basic(optional = false)
    @Column(name = "gpsPresent")
    private int gpsPresent;
    @Basic(optional = false)
    @Column(name = "potenc_est")
    private int potencEst;

    public PollsSatelitetelemetry() {
    }

    public PollsSatelitetelemetry(Integer id, String sync, int contadorMensagens, int nroPacote1, int nroPacote2, int nroPacote3, double latitudeGps, double longitudeGps, int dia, int mes, int ano, int hora, int minuto, int segundo, int hdop, int nroSatelite, double speed, double curso, double altitudeGps, double potRecBal, double tempInterUm, double tempExt, double tempInterDois, double pressao, double pressaoMar, int altitudeBMP, double potTxNoBalao, int statusHeader, int statusTemperatura, int ds18b20, int bmp180, int sdcard, int gpsPresent, int potencEst) {
        this.id = id;
        this.sync = sync;
        this.contadorMensagens = contadorMensagens;
        this.nroPacote1 = nroPacote1;
        this.nroPacote2 = nroPacote2;
        this.nroPacote3 = nroPacote3;
        this.latitudeGps = latitudeGps;
        this.longitudeGps = longitudeGps;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
        this.hdop = hdop;
        this.nroSatelite = nroSatelite;
        this.speed = speed;
        this.curso = curso;
        this.altitudeGps = altitudeGps;
        this.potRecBal = potRecBal;
        this.tempInterUm = tempInterUm;
        this.tempExt = tempExt;
        this.tempInterDois = tempInterDois;
        this.pressao = pressao;
        this.pressaoMar = pressaoMar;
        this.altitudeBMP = altitudeBMP;
        this.potTxNoBalao = potTxNoBalao;
        this.statusHeader = statusHeader;
        this.statusTemperatura = statusTemperatura;
        this.ds18b20 = ds18b20;
        this.bmp180 = bmp180;
        this.sdcard = sdcard;
        this.gpsPresent = gpsPresent;
        this.potencEst = potencEst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public int getContadorMensagens() {
        return contadorMensagens;
    }

    public void setContadorMensagens(int contadorMensagens) {
        this.contadorMensagens = contadorMensagens;
    }

    public int getNroPacote1() {
        return nroPacote1;
    }

    public void setNroPacote1(int nroPacote1) {
        this.nroPacote1 = nroPacote1;
    }

    public int getNroPacote2() {
        return nroPacote2;
    }

    public void setNroPacote2(int nroPacote2) {
        this.nroPacote2 = nroPacote2;
    }

    public int getNroPacote3() {
        return nroPacote3;
    }

    public void setNroPacote3(int nroPacote3) {
        this.nroPacote3 = nroPacote3;
    }

    public double getLatitudeGps() {
        return latitudeGps;
    }

    public void setLatitudeGps(double latitudeGps) {
        this.latitudeGps = latitudeGps;
    }

    public double getLongitudeGps() {
        return longitudeGps;
    }

    public void setLongitudeGps(double longitudeGps) {
        this.longitudeGps = longitudeGps;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }

    public int getHdop() {
        return hdop;
    }

    public void setHdop(int hdop) {
        this.hdop = hdop;
    }

    public int getNroSatelite() {
        return nroSatelite;
    }

    public void setNroSatelite(int nroSatelite) {
        this.nroSatelite = nroSatelite;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCurso() {
        return curso;
    }

    public void setCurso(double curso) {
        this.curso = curso;
    }

    public double getAltitudeGps() {
        return altitudeGps;
    }

    public void setAltitudeGps(double altitudeGps) {
        this.altitudeGps = altitudeGps;
    }

    public double getPotRecBal() {
        return potRecBal;
    }

    public void setPotRecBal(double potRecBal) {
        this.potRecBal = potRecBal;
    }

    public double getTempInterUm() {
        return tempInterUm;
    }

    public void setTempInterUm(double tempInterUm) {
        this.tempInterUm = tempInterUm;
    }

    public double getTempExt() {
        return tempExt;
    }

    public void setTempExt(double tempExt) {
        this.tempExt = tempExt;
    }

    public double getTempInterDois() {
        return tempInterDois;
    }

    public void setTempInterDois(double tempInterDois) {
        this.tempInterDois = tempInterDois;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    public double getPressaoMar() {
        return pressaoMar;
    }

    public void setPressaoMar(double pressaoMar) {
        this.pressaoMar = pressaoMar;
    }

    public int getAltitudeBMP() {
        return altitudeBMP;
    }

    public void setAltitudeBMP(int altitudeBMP) {
        this.altitudeBMP = altitudeBMP;
    }

    public double getPotTxNoBalao() {
        return potTxNoBalao;
    }

    public void setPotTxNoBalao(double potTxNoBalao) {
        this.potTxNoBalao = potTxNoBalao;
    }

    public int getStatusHeader() {
        return statusHeader;
    }

    public void setStatusHeader(int statusHeader) {
        this.statusHeader = statusHeader;
    }

    public int getStatusTemperatura() {
        return statusTemperatura;
    }

    public void setStatusTemperatura(int statusTemperatura) {
        this.statusTemperatura = statusTemperatura;
    }

    public int getDs18b20() {
        return ds18b20;
    }

    public void setDs18b20(int ds18b20) {
        this.ds18b20 = ds18b20;
    }

    public int getBmp180() {
        return bmp180;
    }

    public void setBmp180(int bmp180) {
        this.bmp180 = bmp180;
    }

    public int getSdcard() {
        return sdcard;
    }

    public void setSdcard(int sdcard) {
        this.sdcard = sdcard;
    }

    public int getGpsPresent() {
        return gpsPresent;
    }

    public void setGpsPresent(int gpsPresent) {
        this.gpsPresent = gpsPresent;
    }

    public int getPotencEst() {
        return potencEst;
    }

    public void setPotencEst(int potencEst) {
        this.potencEst = potencEst;
    }
    
}
