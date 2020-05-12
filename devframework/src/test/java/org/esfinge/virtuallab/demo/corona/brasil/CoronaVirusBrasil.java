package org.esfinge.virtuallab.demo.corona.brasil;

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
public class CoronaVirusBrasil
{
	private static final List<CoronaDataBrasil> coronadataBrasil = new ArrayList<>();
	private static final Map<String,String> map = new HashMap<String, String>();
	static {
		try {
	        URL url;
			url = new URL("https://mobileapps.saude.gov.br/esus-vepi/files/unAFkcaNDeXajurGB7LChj8SgQYS2ptm/0f7290d807e00e3dfe98197d2586f1c2_arquivo_srag20200420.csv");
			URI uri = url.toURI();
	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	        String s;
	          while ((s = br.readLine()) != null) {
	                if(!(s.equals("regiao;estado;data;casosNovos;casosAcumulados;obitosNovos;obitosAcumulados")))
	                {
	                	CoronaDataBrasil c1 = new CoronaDataBrasil();
		               String[] dados = s.split(";");
		               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		               Date date = sdf.parse(dados[2]);

		               Calendar cal = Calendar.getInstance();

		               cal.setTime(date);
		               c1.setData(cal);
		               
		               map.put(dados[1],dados[1]);
		               c1.setRegiao(dados[0]);
		               c1.setEstado(dados[1]);
		               c1.setCasosNovos(Integer.parseInt(dados[3]));
		               c1.setCasosAcumulados(Integer.parseInt(dados[4]));
		               c1.setObitosNovos(Integer.parseInt(dados[5]));
		               c1.setObitosAcumulados(Integer.parseInt(dados[6]));
		               coronadataBrasil.add(c1);
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
	public List<CoronaDataBrasil> listAllData()
	{
		
		return coronadataBrasil;
	}
	
	@ServiceMethod(
			label = "Retorna os dados do coronavirus por pais que for selecionado",
			description = "Seleciona os casos dos países")
		@TableReturn
		public List<CoronaDataBrasil> listState(@Combo(value = "estado") String estado)
		{	
			List<CoronaDataBrasil> paisCase =new ArrayList<CoronaDataBrasil>();
			
			for (CoronaDataBrasil coronaData : coronadataBrasil) {
				if(coronaData.getEstado().equals(estado)) {
					paisCase.add(coronaData);
				}
			}
			
			return paisCase;
		}
	
		@ComboMethod("estado")
		public Map<String,String> estadoList()
		{
				return map;
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
				xAxisLabel = "Dia",
				xAxis = {"data"},
				yAxis= {"casosNovos","casosAcumulados"})

		public List<CoronaDataBrasil> createGraphPerState(@Combo(value = "estado") String estado)
		{
			return listState(estado);
		}
}
