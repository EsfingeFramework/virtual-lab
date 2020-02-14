package org.esfinge.virtuallab.spring;

import static org.junit.Assert.assertEquals;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.services.ClassLoaderService;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.services.invoker.InvokerValidClass;
import org.esfinge.virtuallab.web.JsonReturn;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


public class MappingRestControllerTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
		

	}
	
	@Before
	public void doBefore()
	{
		// apaga o diretorio de testes antes de cada teste
		TestUtils.cleanTestDir();
	}
	
	@Test
	public void loadWelcome() throws Exception{
		String uri ="/welcome";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	    	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	     
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals("Welcome", content);
	}
	
	@Test
	public void loadClass() throws Exception{
		String uri ="/services";
		
		
		
		TestUtils.createJar("InvokerValidClass.jar", InvokerValidClass.class);
		String jarPath = TestUtils.pathFromTestDir("InvokerValidClass.jar");
		
	
		
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	    	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	     
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(406, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      System.out.println(content);
	}
	
}
