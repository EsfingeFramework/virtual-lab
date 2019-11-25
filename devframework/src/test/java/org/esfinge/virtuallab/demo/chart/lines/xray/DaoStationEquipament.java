package org.esfinge.virtuallab.demo.chart.lines.xray;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;


@ServiceDAO(
		label = "DAOXRayLow",
		description = "Demonstração da anotação @ServiceDAO.",
		url = "jdbc:postgresql://localhost:5432/climaespacial",
		user = "postgres", 					    
		password = "postgres", 
		dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoStationEquipament extends Repository<StationEquipament> {
   
    @ServiceMethod(
			label = "Listar os valores das estações.",
			description = "Retorna todas as estações")
	@TableReturn
	public List<Station> getStationEquipament();
    
    @ServiceMethod(
			label = "Listar os valores das estações.",
			description = "Retorna as estações com o nome")
	@TableReturn
	public List<Station> getStationEquipamentByStationFkNameStation(String name);
    
    @ServiceMethod(
			label = "Listar os valores das estações.",
			description = "Retorna a estação")
	@TableReturn
	public List<Station> getStationEquipamentByStation(Station station);


	
}
