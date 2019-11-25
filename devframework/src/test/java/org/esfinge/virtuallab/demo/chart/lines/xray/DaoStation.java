package org.esfinge.virtuallab.demo.chart.lines.xray;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;


@ServiceDAO(
		label = "DAOStation",
		description = "Busca os satelites usados",
		url = "jdbc:postgresql://localhost:5432/climaespacial",
		user = "postgres", 					    
		password = "postgres", 
		dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoStation extends Repository<Station>
{
    @ServiceMethod(
			label = "Listar os valores das estações.",
			description = "Retorna todas as estações")
	@TableReturn
	public List<Station> getStation();
    
    @ServiceMethod(
			label = "Listar os valores de goes.xray_data_low",
			description = "Retorna todas as colunas que contenham o nome da estação")
	@TableReturn
	public List<Station> getStationByNameStationOrderById(String nameStation);

}
