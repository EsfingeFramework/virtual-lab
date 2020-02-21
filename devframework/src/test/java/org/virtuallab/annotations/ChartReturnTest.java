package org.virtuallab.annotations;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessor;
import org.esfinge.virtuallab.metadata.processors.MethodReturnProcessorHelper;
import org.esfinge.virtuallab.services.InvokerService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.spring.JpaConfiguration;
import org.esfinge.virtuallab.web.JsonReturn;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfiguration.class})
public class ChartReturnTest {
	
	//Não é serie temporal
	@Test
	public void test() throws Exception {
		MethodDescriptor md = new  MethodDescriptor();
		TestUtils.createJar("ChartTest.jar", Chart.class, Data2Val.class);
		String x = TestUtils.pathFromTestDir("ChartTest.jar");
		
		System.out.println(x);
		File initialFile = new File(x);

		InputStream fileStream = new DataInputStream(new FileInputStream(initialFile)); ;
		PersistenceService.getInstance().saveUploadedFile(fileStream , "ChartTest.jar");
		
		List<MethodDescriptor> methodList = PersistenceService.getInstance().listServiceMethods(Chart.class.getCanonicalName());
		for (MethodDescriptor methodDescriptor : methodList) {
			System.out.println(methodDescriptor.getName());
			if(methodDescriptor.getClassName().contains("tableReturnTest"));
			{
				MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance().findProcessor(methodDescriptor);
				Object result = InvokerService.getInstance().call(methodDescriptor);
				JsonReturn ret = new JsonReturn();
				ret.setData(returnProcessor.process(result));
				assertEquals("{\"rows\":[[1,1],[2,1],[3,1]],\"header\":[\"X1\",\"Y1\"],\"showHeader\":true}" + 
						"", ret.getData().toString());
				
				System.out.println(ret.toString());

			}
			if(methodDescriptor.getClassName().contains("tableReturnTest"));
			{
				MethodReturnProcessor<?> returnProcessor = MethodReturnProcessorHelper.getInstance().findProcessor(methodDescriptor);
				Object result = InvokerService.getInstance().call(methodDescriptor);
				JsonReturn ret = new JsonReturn();
				ret.setData(returnProcessor.process(result));
				assertEquals("{\"rows\":[[1,1],[2,1],[3,1]],\"header\":[\"X1\",\"Y1\"],\"showHeader\":true}" + 
						"", ret.getData().toString());
				
				System.out.println(ret.toString());

			}

		}
		
	}
	
}
