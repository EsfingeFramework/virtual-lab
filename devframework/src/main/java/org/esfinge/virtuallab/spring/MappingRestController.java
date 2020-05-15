package org.esfinge.virtuallab.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.descriptors.ClassDescriptor;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessorHelper;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.JsonReturn;
import org.esfinge.virtuallab.web.json.JsonArray;
import org.esfinge.virtuallab.web.json.JsonData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController

@Controller
public class MappingRestController {

	@RequestMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}

	@GetMapping(value = "/services/{className}/{methodName}/exec",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object message(@PathVariable String className, @PathVariable String methodName,
			@RequestParam(required = false) Map<String, String> param) throws Exception {

		MethodDescriptor methodDescriptor = new MethodDescriptor();
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		List<MethodDescriptor> md =null;
		for (ClassDescriptor classDescriptor : classesList) {
			if(classDescriptor.getQualifiedName().contains(className))
			{
				md = PersistenceService.getInstance().listServiceMethods(classDescriptor.getQualifiedName());
				
			}
		}
		
			for (MethodDescriptor methodDesc : md) {
				if(methodDesc.getName().equals(methodName))
				{
					methodDescriptor = methodDesc;
				}
			}
		
		
		
		int index = 0;
		List<ParameterDescriptor> parameters = methodDescriptor.getParameters();
		
		//"paramValues":{"param1":"as","param2":12}
		
		String jsonParamValues;
		
		ObjectMapper objectMapper = new ObjectMapper();
		

            jsonParamValues = objectMapper.writeValueAsString(param);
            System.out.println("json = " + jsonParamValues);
        
		
		System.out.println("83"+jsonParamValues);

		List<Object> values = methodDescriptor.getParameters()
				.stream()
				.sorted()
				.map((p) -> {
					try
					{
						// obtem o valor do parametro (como JSON)
						JsonNode jsonNode = JsonUtils.fromStringToJsonNode(jsonParamValues).get(p.getName());
						System.out.println();
						String data2 =p.getDataType();
						// converte o valor JSON para o tipo correto
						return JsonUtils.convertTo(jsonNode.toString(), ReflectionUtils.findClass(data2));
					}
					catch ( Exception e )
					{
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());
		
		//for (Map.Entry<String, Object> requestParams : allRequestParams.entrySet()) {
		//	ParameterDescriptor parameter = new ParameterDescriptor();
		//	parameter.setDataType(requestParams.getKey());
		//	parameter.setIndex(index++);
		//	parameters.add(parameter);
		//	values.add(JsonUtils.convertTo(requestParams.getValue().toString(), ReflectionUtils.findClass(requestParams.getKey())));
		//}
		// invoca o metodo
		Object result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
		
		// obtem o processor apropriado para o retorno do metodo
		MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance().findProcessor(methodDescriptor);

		
		//MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance()
		//		.findProcessor(methodDescriptor);
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setData(returnProcessor.process(result));
		jsonReturn.setType(returnProcessor.getType());
		jsonReturn.setSuccess(true);
		jsonReturn.setMessage("");
		return jsonReturn;
	}
	
	@GetMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listAllClass() {
		
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setData(new JsonArray<ClassDescriptor>(classesList));
		jsonReturn.setSuccess(true);
		return jsonReturn;

	}
	
	@GetMapping(value = "/services/{className}",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listMethodsSameClass(@PathVariable String className) {
		
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		
		List<MethodDescriptor> md = null;
		for (ClassDescriptor classDescriptor : classesList) {
			if(classDescriptor.getQualifiedName().contains(className))
			{
				md = PersistenceService.getInstance().listServiceMethods(classDescriptor.getQualifiedName());
			}
		}
		
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setData(new JsonArray<MethodDescriptor>(md));
		jsonReturn.setSuccess(true);
		return jsonReturn;

	}
	
	@GetMapping(value = "/services/{className}/{methodName}",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listFieldsSameMethods(@PathVariable String className,@PathVariable String methodName) {
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		
		List<MethodDescriptor> md = null;
		for (ClassDescriptor classDescriptor : classesList) {
			if(classDescriptor.getQualifiedName().contains(className))
			{
				md = PersistenceService.getInstance().listServiceMethods(classDescriptor.getQualifiedName());
			}
		}
		
		MethodDescriptor method = null;
		for(MethodDescriptor methodDescriptor : md)
		{
			if(methodDescriptor.getName().contains(methodName))
			{
				method = methodDescriptor;
				System.out.println("OK "+methodDescriptor);
			}
		}
		
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setData(new JsonArray<MethodDescriptor>(method));
		jsonReturn.setSuccess(true);
		return jsonReturn;

	}
	
	
}
