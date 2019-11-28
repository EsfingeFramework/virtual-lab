package org.virtuallab.annotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.LineChartReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;

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
	
	@ServiceMethod(description = "Test",label = "TableReturn")
	@TableReturn
	public List<Data2Val> tableReturnTest()
	{
		return list;
	}
}
