package org.esfinge.virtuallab.demo.chart.lines.xray;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.springframework.format.annotation.DateTimeFormat;

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
public interface DaoXrayLow extends Repository<XrayDataLow>
{

	    /*    	    
	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low filtrando por Header",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
	    public List<XrayDataLow> getXrayDataLowByHeaderOrderById(Header header);
	    */
	    	
	    @ServiceMethod(
				label = "Listar os valores de goes.xray_data_low filtrando por Header",
				description = "Retorna todos os elementos da tabela goes.xray_data_low")
		@TableReturn
	    public List<XrayDataLow> getXrayDataLowByHeaderOrderById(Long header );

}