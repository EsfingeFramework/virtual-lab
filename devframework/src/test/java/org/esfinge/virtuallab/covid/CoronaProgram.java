package org.esfinge.virtuallab.covid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.Combo;
import org.esfinge.virtuallab.api.annotations.ComboMethod;
import org.esfinge.virtuallab.api.annotations.Inject;
import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;


@ServiceClass(
	label = "Dados do Coronavirus",
	description = "Demonstração de recuperação dos dados sobre o coronavirus")
public class CoronaProgram
{
	
	
	@Inject
	private CoronaDao cd;
	
	/*--------------------------------------------------------------------------
	 * Utiliza a anotacao sem parametros.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Returns coronavirus data by date",
		description = "Returns coronavirus data by date")
	@TableReturn
	public List<CoronaVirusData> listAllData()
	{
		List<CoronaVirusData> retorno =  this.cd.getCoronaVirusDataOrderByData();

		return retorno;
	}
	
	@ServiceMethod(
			label = "Returns coronavirus data by selected country",
			description = "Returns coronavirus data by selected country")
		@TableReturn
		public List<CoronaVirusData> listByLocation(@Combo(value = "location") String location)
		{	
			List<CoronaVirusData> retorno = this.cd.getCoronaVirusDataByLocationOrderByData(location);
			return retorno;
		}
	
	@ComboMethod(value = "location")
	public Map<String,String> locationCombo()
	{
		Map<String, String> paisMap = new HashMap<String, String>();
		for (CoronaVirusData iterable_element : listAllData()) {
			paisMap.put(iterable_element.getLocation(), iterable_element.getLocation());
		}
		
		return paisMap;
	}
	
	@ServiceMethod(
			label = "Returns the top 10 coronavirus by country in table",
			description = "Returns the top 10 coronavirus by country in table")
		@TableReturn

	public List<CoronaVirusData> listByPerNumberOfDeathsToday()
	{
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DAY_OF_MONTH, -1);
		List<CoronaVirusData> value = cd.getCoronaVirusDataByDataOrderByNewDeathsDesc(dataAtual);
		
		List<CoronaVirusData> coronatTop10= new ArrayList<CoronaVirusData>();
		
		for (int i = 1; i <= 10; i++) {
			coronatTop10.add(value.get(i));
		}
		
		return coronatTop10;
	}
	
	@ServiceMethod(
			label = "Returns the top 10 coronavirus by country in bar chart.",
			description = "Returns the top 10 coronavirus by country in bar chart.")
	@BarChartReturn(
			dataValuesField = "totalDeaths",
			dataLabelsField = "location",
			xAxisLabel ="Location",
			yAxisLabel = "Total Deaths")

	public List<CoronaVirusData> listByPerNumberGraphOfDeathsToday()
	{
		
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DAY_OF_MONTH, -1);
		List<CoronaVirusData> value = cd.getCoronaVirusDataByDataOrderByNewDeathsDesc(dataAtual);
		
		List<CoronaVirusData> coronatTop10= new ArrayList<CoronaVirusData>();
		
		for (int i = 1; i <= 10; i++) {
			coronatTop10.add(value.get(i));
		}
		
		return coronatTop10;
	}
	
	
	@ServiceMethod(
			label ="Returns the line graph of CoronaDao.java \n" +
					"CoronaProgram.java \n" +
					"CoronaVirusData.java of coronavirus by parents that is selected",
			description = "Returns the line graph of CoronaDao.java \n" +
					"CoronaProgram.java \n" +
					"CoronaVirusData.java of coronavirus by parents that is selected")
	@LineChartReturn(typeOfChart = "line",
			dataLabels = "eventDateTime",
			temporalSeries = true,
			multipleDataset = true,
			xAxisShowGridlines = true, 
			title = "New Cases of Corona Virus in the country",
			yAxisLabel = "Cases",
			xAxisLabel = "Date",
			xAxis = {"data"},
			yAxis= {"newCases"})
		public List<CoronaVirusData> graphByLocation(@Combo(value = "location") String location)
		{	
			List<CoronaVirusData> retorno = this.cd.getCoronaVirusDataByLocationOrderByData(location);
			List<CoronaVirusData> retorno2 = new ArrayList<CoronaVirusData>();
			for (int i = retorno.size()-50; i < retorno.size(); i++) {
				
				if(i>=2)
				{
					CoronaVirusData cd1 = retorno.get(i);
					CoronaVirusData cd2 = retorno.get(i-1);
					CoronaVirusData cd3 = retorno.get(i-2);
					
					int value = cd1.getNewCases()+cd2.getNewCases()+cd3.getNewCases();
					
					value = value/3;
					cd1.setNewCases(value);
					
					retorno2.add(cd1);
				}
				else
				{
					retorno2.add(retorno.get(i));
				}
				
			}
			
			return retorno2;
		}


	
}
