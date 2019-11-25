package org.esfinge.virtuallab.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
			@RequestParam HashMap<String, Object> allRequestParams) throws Exception {

		MethodDescriptor methodDescriptor = new MethodDescriptor();
		List<ClassDescriptor> classesList = PersistenceService.getInstance().listServiceClasses();
		List<MethodDescriptor> md =null;
		for (ClassDescriptor classDescriptor : classesList) {
			if(classDescriptor.getQualifiedName().contains(className))
			{
				md = PersistenceService.getInstance().listServiceMethods(classDescriptor.getQualifiedName());
				
			}
		}
		if(md.size()==1)
		{
			methodDescriptor = md.get(0);
		}
		
		int index = 0;
		List<ParameterDescriptor> parameters = new ArrayList<ParameterDescriptor>();
		List<Object> values = new ArrayList<Object>();
		for (Map.Entry<String, Object> requestParams : allRequestParams.entrySet()) {
			ParameterDescriptor parameter = new ParameterDescriptor();
			parameter.setDataType(requestParams.getKey());
			parameter.setIndex(index++);
			parameters.add(parameter);
			values.add(JsonUtils.convertTo(requestParams.getValue().toString(), ReflectionUtils.findClass(requestParams.getKey())));
		}
		methodDescriptor.setParameters(parameters);
	
		Object result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
		MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance()
				.findProcessor(methodDescriptor);
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setData(returnProcessor.process(result));
		jsonReturn.setType(returnProcessor.getType());
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
}
