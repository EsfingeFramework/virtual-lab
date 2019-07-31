package org.esfinge.virtuallab.web.op;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Trata as requisicoes de listar os metodos de servicos das classes salvas.
 */
public class ListServiceMethodsHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException
	{
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();

		try
		{
			// obtem o objeto JSON do request
			JsonNode jsonClass = this.getJsonParameter(request);

			// obtem a lista de servicos validos da classe informada
			String clazzQualifiedName = jsonClass.get("clazz").asText();
			List<MethodDescriptor> methodList = PersistenceService.getInstance().listServiceMethods(clazzQualifiedName);

			if (!Utils.isNullOrEmpty(methodList))
			{
				JsonArray jarray = gson.toJsonTree(methodList).getAsJsonArray();
				jsonObject.add("methods", jarray);
				jsonObject.addProperty("clazz", clazzQualifiedName);
				jsonObject.addProperty("message", "");
				jsonObject.addProperty("success", true);
			}
			else
			{
				jsonObject.addProperty("message", "Classe pesquisada não encontrada!");
				jsonObject.addProperty("success", false);
			}
		}
		catch (Exception e)
		{
			// TODO: debug..
			e.printStackTrace();
			
			jsonObject.addProperty("message", "Erro: " + e.toString());
			jsonObject.addProperty("success", false);
		}

		return jsonObject;

	}
}
