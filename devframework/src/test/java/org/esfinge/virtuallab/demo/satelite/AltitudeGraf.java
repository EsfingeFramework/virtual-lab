package org.esfinge.virtuallab.demo.satelite;

import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass(
		label = "GRÁFICOS - LINHAS",
		description = "Demonstração da anotação @LineChartReturn.")
public class AltitudeGraf {
	
	
	
	List<PollsSatelitetelemetry> resp;
	
	public AltitudeGraf() {
		// TODO Auto-generated constructor stub
		resp = new ArrayList<PollsSatelitetelemetry>();
		resp.add( new PollsSatelitetelemetry(1,223.0));
		resp.add( new PollsSatelitetelemetry(2,323.0));
		resp.add( new PollsSatelitetelemetry(3,423.0));
		resp.add( new PollsSatelitetelemetry(4,523.0));
	
	}
	
	@ServiceMethod(
			label = "Criar gráfico - Lista",
			description = "@ChartReturn com parametros."
			)
	@LineChartReturn(dataLabels = "eventDateTime",
				temporalSeries = false,
				multipleDataset = false,
				xAxisShowGridlines = false, 
				xAxis = {"id"},
				yAxis= {"altitudeGps"},
				yAxisScales = {220.0,350.0}
			)
	public List<?> listaXrayDataLowGetXray()
	{
		return resp;	
	}	


}
