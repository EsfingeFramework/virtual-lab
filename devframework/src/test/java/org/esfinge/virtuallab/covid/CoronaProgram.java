package org.esfinge.virtuallab.covid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esfinge.virtuallab.api.annotations.Inject;
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
		public List<CoronaDados> listPais(String pais)
		{	
			List<CoronaDados> retorno = this.cd.getCoronaDadosByLocation(pais);
			return retorno;
		}
	
		public Map<String,String> paisList()
		{
			Map<String,String> map = new HashMap<String, String>();
			map.put("Brazil","Brazil");
			map.put("EUA","EUA");
			System.out.println("RODOU-----------------");
			return map;
		}

		@Override
		public String toString() {
			return "CoronaVirus []"+paisList();
		}

		
}
