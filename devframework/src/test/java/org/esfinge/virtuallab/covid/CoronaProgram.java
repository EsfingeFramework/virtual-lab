package org.esfinge.virtuallab.covid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		label = "Retorna os dados do coronavirus",
		description = "sem parâmetros.")
	@TableReturn
	public List<CoronaDados> listAllData()
	{
		List<CoronaDados> retorno =  this.cd.getCoronaDados();

		return retorno;
	}
	
	@ServiceMethod(
			label = "Retorna os dados do coronavirus por pais que for selecionado",
			description = "Seleciona os casos dos países")
		@TableReturn
		public List<CoronaDados> listByLocation(@Combo(value = "location") String location)
		{	
			List<CoronaDados> retorno = this.cd.getCoronaDadosByLocation(location);
			return retorno;
		}
	
	@ComboMethod(value = "location")
	public Map<String,String> locationCombo()
	{
		Map<String, String> paisMap = new HashMap<String, String>();
		for (CoronaDados iterable_element : listAllData()) {
			paisMap.put(iterable_element.getLocation(), iterable_element.getLocation());
		}
		
		return paisMap;
	}
	
	@ServiceMethod(
			label = "Retorna os dados do coronavirus por pais que for selecionado",
			description = "Seleciona os casos dos países")
		@TableReturn

	public List<CoronaDados> listByPerNumberOfDeathsToday()
	{
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DAY_OF_MONTH, -1);
		List<CoronaDados> value = cd.getCoronaDadosByDataOrderByNewDeathsDesc(dataAtual);
		
		List<CoronaDados> coronatTop10= new ArrayList<CoronaDados>();
		
		for (int i = 1; i <= 10; i++) {
			coronatTop10.add(value.get(i));
		}
		
		return coronatTop10;
	}
	
	@ServiceMethod(
			label = "Retorna os dados do coronavirus por pais que for selecionado",
			description = "Seleciona os casos dos países")
	@LineChartReturn(typeOfChart = "line",
			dataLabels = "eventDateTime",
			temporalSeries = true,
			multipleDataset = true,
			xAxisShowGridlines = true, 
			title = "Novos Casos de Corona Virus no pais",
			yAxisLabel = "Casos",
			xAxisLabel = "Data",
			xAxis = {"data"},
			yAxis= {"newCases"})
		public List<CoronaDados> graphByLocation(@Combo(value = "location") String location)
		{	
			List<CoronaDados> retorno = this.cd.getCoronaDadosByLocation(location);
			List<CoronaDados> retorno2 = new ArrayList<CoronaDados>();
			for (int i = retorno.size()-50; i < retorno.size(); i++) {
				retorno2.add(retorno.get(i));
			}
			
			return retorno2;
		}


	
}
