package org.esfinge.virtuallab.web.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Classe representando informacoes basicas de Schema de elementos JSON.
 */
public class JsonSchemaElement implements JsonSchema
{
	// tipo do elemento
	private String type;
	
	// label do elemento
	private String title;
	
	// label do class
	private String clazz;
	
	// se o preenchimento do elemento eh obrigatorio
	private boolean required;
	
	// formato do tipo do elemento
	private String format;

	private Map<String, String> selectElement = null;
	
	public String getClazz() {
		return clazz;
	}

	
	
	public Map<String, String> getSelectElement() {
		return selectElement;
	}



	public void setSelectElement(Map<String, String> selectElement) {
		this.selectElement = selectElement;
	}



	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder("{ ");
		builder.append(String.format("\"type\" : \"%s\", ", this.type));
		builder.append(String.format("\"title\" : \"%s\", ", this.title));
		builder.append(String.format("\"required\" : \"%b\"", this.required));
		if(this.clazz!=null)
		{
			builder.append(String.format(",\"htmlClass\" : \"%s\"", this.clazz));
		}
		if(this.selectElement!=null)
		{
			///TODO
			List<String> l = new ArrayList<String>();
			for (Map.Entry<String,String> pair : selectElement.entrySet()) {
			   
			    l.add("\""+pair.getValue()+"\"");
			  }
			Collections.sort(l);
			builder.append(String.format(",\"enum\" : %s", l ));
		}
		
		if ( this.format != null )
			builder.append(String.format(", \"format\" : \"%s\"", this.format));
		
		builder.append(" }");
		return builder.toString();
	}
}
