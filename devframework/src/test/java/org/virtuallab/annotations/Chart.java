package org.virtuallab.annotations;

import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass(
		label = "GRÁFICOS - LINHAS",
		description = "Demonstração da anotação @LineChartReturn.")
public class Chart {
	List<Data2Val> list;
	
	public Chart() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<Data2Val>();
		list.add(new Data2Val(1, 1));
		list.add(new Data2Val(2, 1));
		list.add(new Data2Val(3, 1));
	}
	
	@ServiceMethod(description = "Test",label = "T")
	@LineChartReturn(typeOfChart = "line",
			dataLabels = "eventDateTime",
			temporalSeries = false,
			multipleDataset = true,
			xAxisShowGridlines = false, 
			title = "Fluxo Raio-X",
			yAxisLabel = "Watts m²",
			xAxisLabel = "Tempo",
			xAxis = {"x1"},
			yAxis= {"y1"})
	public List<Data2Val> lista()
	{
		return list;
	}
	
	
}
