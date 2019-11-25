package org.esfinge.virtuallab.demo.chart.lines.xray;

import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;
import net.sf.esfinge.querybuilder.annotation.Condition;
import net.sf.esfinge.querybuilder.annotation.DomainTerm;
import net.sf.esfinge.querybuilder.annotation.GreaterOrEquals;
import net.sf.esfinge.querybuilder.methodparser.ComparisonType;

/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @ServiceDAO.
 *-------------------------------------------------------------------------*/
@ServiceDAO(
	label = "DAOXRayLow",
	description = "Demonstração da anotação @ServiceDAO.",
	url = "jdbc:postgresql://localhost:5432/climaespacial",
	user = "postgres", 					    
	password = "postgres", 
	dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoHeader extends Repository<Header>
{

	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
		public List<Header> getHeader();
	    


	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
		public List<Header> getHeaderByEquipament(long e);

}