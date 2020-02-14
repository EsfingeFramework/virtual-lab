package org.esfinge.virtuallab.demo.satelite;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.demo.dao.Temperatura;

import net.sf.esfinge.querybuilder.Repository;

@ServiceDAO(
		label = "DAO",
		description = "Demonstração da anotação @ServiceDAO.",
		url = "jdbc:postgresql://localhost:5432/postgres", 
		user = "postgres", 
		password = "postgres", 
		dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoSatelite extends Repository<PollsSatelitetelemetry>
{
	  @ServiceMethod(
			label = "Listar todas temperaturas",
			description = "Retorna todos as temperaturas cadastradas no Banco de Dados.")
		@TableReturn
		public List<PollsSatelitetelemetry> getPollsSatelitetelemetry();
		
}
