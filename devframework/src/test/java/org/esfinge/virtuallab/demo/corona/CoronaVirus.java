package org.esfinge.virtuallab.demo.corona;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.esfinge.virtuallab.api.annotations.BarChartReturn;
import org.esfinge.virtuallab.api.annotations.Combo;
import org.esfinge.virtuallab.api.annotations.ComboMethod;
import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.api.annotations.Param;
import org.esfinge.virtuallab.api.annotations.ParamAttribute;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.demo.corona.brasil.CoronaDataBrasil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.net.URISyntaxException;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @BarChartReturn.
 *-------------------------------------------------------------------------*/
@ServiceClass(
	label = "Dados do Coronavirus",
	description = "Demonstração de recuperação dos dados sobre o coronavirus")
public class CoronaVirus
{
	private static final List<CoronaData> coronadata = new ArrayList<>();
	private static final Map<String,String> map = new HashMap<String, String>();
	static {
		try {
	        URL url;
			url = new URL("https://covid.ourworldindata.org/data/ecdc/full_data.csv");
			URI uri = url.toURI();
	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	        String s;
	          while ((s = br.readLine()) != null) {
	                if(!(s.equals("date,location,new_cases,new_deaths,total_cases,total_deaths")))
	                {
	                	CoronaData c1 = new CoronaData();
		               String[] dados = s.split(",");
		               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		               Date date = sdf.parse(dados[0]);

		               Calendar cal = Calendar.getInstance();

		               cal.setTime(date);
		               c1.setData(cal);
		               
		               c1.setLocation(dados[1]);
		               map.put(dados[1],dados[1]);
		               c1.setNewCases(Integer.parseInt(dados[2]));
		               c1.setNewDeaths(Integer.parseInt(dados[3]));
		               c1.setTotalCases(Integer.parseInt(dados[4]));
		               c1.setTotalDeaths(Integer.parseInt(dados[5]));
		               coronadata.add(c1);
	                }
	          }
		} catch ( IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}	
	
	/*--------------------------------------------------------------------------
	 * Utiliza a anotacao sem parametros.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Retorna os dados do coronavirus",
		description = "sem parâmetros.")
	@TableReturn
	public List<CoronaData> listAllData()
	{
		
		return coronadata;
	}
	
	@ServiceMethod(
			label = "Retorna os dados do coronavirus por pais que for selecionado",
			description = "Seleciona os casos dos países")
		@TableReturn
		public List<CoronaData> listPais(@Combo(value = "pais") String pais)
		{	
			List<CoronaData> paisCase =new ArrayList<CoronaData>();
			
			for (CoronaData coronaData : coronadata) {
				if(coronaData.getLocation().equals(pais)) {
					paisCase.add(coronaData);
				}
			}
			
			return paisCase;
		}
	
		
	@ServiceMethod(
			label = "Criar gráfico - Lista",
			description = "@ChartReturn com parametros."
			)
	@LineChartReturn(typeOfChart = "line",
			dataLabels = "eventDateTime",
			temporalSeries = true,
			multipleDataset = true,
			xAxisShowGridlines = false, 
			title = "Casos de Corona Virus por estado",
			yAxisLabel = "Casos",
			xAxisLabel = "Data",
			xAxis = {"data"},
			yAxis= {"newCases","totalCases"})
	public List<CoronaData> createGraph(@Combo(value = "pais") String pais)
	{
		return listPais(pais);
	}

	
	@ComboMethod("pais")
	public Map<String,String> paisList()
	{			
		return map;
	}
	
	@ServiceMethod(
			label = "Criar gráfico - Lista",
			description = "@ChartReturn com parametros."
			)
	@TableReturn
	public List<CoronaData> listarUltimoDia()
	{
		List<CoronaData> paisCase =new ArrayList<CoronaData>();
		
		Calendar calendar = Calendar.getInstance();
		long time = System.currentTimeMillis();
		calendar.setTimeInMillis(time);
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		
		String dataDeHoje = formato.format(calendar.getTime());
		
		for (CoronaData coronaData : coronadata) {
			String dataBanco = formato.format(coronaData.getData().getTime());
			if(dataBanco.equals(dataDeHoje)) {
				paisCase.add(coronaData);
			}
		}
		return paisCase;
	}
	
	@ServiceMethod
	@TableReturn
	public List<CoronaData> listarUltimoDiaPorNovosCasos()
	{
		List<CoronaData> paisCase =listarUltimoDia();		
		return paisCase;
	}
	
	

}


