package org.esfinge.virtuallab.covid;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

import net.sf.esfinge.querybuilder.Repository;
import net.sf.esfinge.querybuilder.annotation.Greater;
import net.sf.esfinge.querybuilder.annotation.GreaterOrEquals;

@ServiceDAO(
		label = "DAO",
		description = "Coronavirus data updated daily at 12:00.",
		url = "jdbc:postgresql://localhost:5432/postgres", 
		user = "postgres", 
		password = "postgres", 
		dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface CoronaDao extends Repository<CoronaVirusData>
	{
		
		@ServiceMethod(
			label = "Lists all coronavirus data ordered by date",
			description = "Lists all coronavirus data ordered by date")
		@TableReturn
		public List<CoronaVirusData> getCoronaVirusDataOrderByData();
		
		@ServiceMethod(
				label = "List only the chosen country ordered by date",
				description = "List only the chosen country ordered by date")
		@TableReturn
		public List<CoronaVirusData> getCoronaVirusDataByLocationOrderByData(String location);		
		
		@ServiceMethod(
				label = "Select all data for a specific day by total deaths",
				description = "Select all data for a specific day by total deaths")
		@TableReturn
		public List<CoronaVirusData> getCoronaVirusDataByDataOrderByTotalDeathsDesc(Calendar data);		
		
		@ServiceMethod(
				label = "Selects all data from a date greater than or equal to the selected date by ordering new deaths",
				description = "Selects all data from a date greater than or equal to the selected date by ordering new deaths")
		@TableReturn
		public List<CoronaVirusData> getCoronaVirusDataByDataOrderByNewDeathsDesc(@GreaterOrEquals Calendar data);		

		@ServiceMethod(
				label = "Selects all data from a date greater than or equal to the selected date by ordering new cases",
				description = "Selects all data from a date greater than or equal to the selected date by ordering new cases")
		@TableReturn
		public List<CoronaVirusData> getCoronaVirusDataByDataOrderByNewCasesDesc(@GreaterOrEquals Calendar data);		

		@ServiceMethod(
				label = "Selects all data from a date greater than or equal to the selected date by ordering Total cases",
				description = "Selects all data from a date greater than or equal to the selected date by ordering Total cases")
		@TableReturn
		public List<CoronaVirusData> getCoronaVirusDataByDataOrderByTotalCasesDesc(@GreaterOrEquals Calendar data);		

	
	}
