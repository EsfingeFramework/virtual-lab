package org.esfinge.virtuallab.spring;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessorHelper;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.web.JsonReturn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Controller
public class MappingRestController {

    @RequestMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("index.jsp");
    }

    @RequestMapping(value = "/services/{className}/{methodName}", method = RequestMethod.GET)
    public Object message(@PathVariable String className, @PathVariable String methodName,
            @RequestParam LinkedHashMap<String, Object> allRequestParams) throws Exception {
        var methodDescriptor = new MethodDescriptor();
        methodDescriptor.setClassName(className);
        methodDescriptor.setName(methodName);
        methodDescriptor.setReturnType("String");
        var index = 0;
        List<ParameterDescriptor> parameters = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (var requestParams : allRequestParams.entrySet()) {
            var parameter = new ParameterDescriptor();
            parameter.setDataType(requestParams.getKey());
            parameter.setIndex(index++);
            parameters.add(parameter);
            values.add(JsonUtils.convertTo(requestParams.getValue().toString(), ReflectionUtils.findClass(requestParams.getKey())));
        }
        methodDescriptor.setParameters(parameters);

        var result = InvokerService.getInstance().call(methodDescriptor, values.toArray());
        var returnProcessor = MethodReturnProcessorHelper.getInstance()
                .findProcessor(methodDescriptor);
        var jsonReturn = new JsonReturn();
        jsonReturn.setData(returnProcessor.process(result));
        jsonReturn.setType(returnProcessor.getType());
        return jsonReturn;
    }
}
