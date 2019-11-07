package org.esfinge.virtuallab.services;

import java.util.Arrays;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.exceptions.ClassLoaderException;
import org.esfinge.virtuallab.exceptions.InvocationException;
import org.esfinge.virtuallab.exceptions.ValidationException;
import org.esfinge.virtuallab.services.invoker.InvokerInvalidClass;
import org.esfinge.virtuallab.services.invoker.InvokerValidClass;
import org.esfinge.virtuallab.spring.JpaConfiguration;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Testes unitarios para a classe InvokerService.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfiguration.class})
public class InvokerServiceTest
{
	@Rule
	public ExpectedException thrown  = ExpectedException.none();
	
	
	// gera o MethodDescriptor do metodo valido da classe de teste valida
	private MethodDescriptor createMethodDescriptor()
	{
		TestUtils.createJar("InvokerValidClass.jar", InvokerValidClass.class);
		String jarPath = TestUtils.pathFromTestDir("InvokerValidClass.jar");
		ClassLoaderService.getInstance().loadService(jarPath);

		
		ParameterDescriptor pd1 = new ParameterDescriptor();
		pd1.setIndex(0);
		pd1.setName("param1");
		pd1.setDataType(String.class.getCanonicalName());
		
		ParameterDescriptor pd2 = new ParameterDescriptor();
		pd2.setIndex(1);
		pd2.setName("param2");
		pd2.setDataType(int.class.getCanonicalName());
		
		MethodDescriptor md = new MethodDescriptor();
		md.setClassName(InvokerValidClass.class.getCanonicalName());
		md.setName("validMethod");
		md.setReturnType(String.class.getCanonicalName());
		md.setParameters(Arrays.asList(pd1, pd2));
		
		
		return md;
	}	
	
	
	@Test(expected = InvocationException.class)
	public void testInvokeOnNull() throws InvocationException
	{
		InvokerService.getInstance().call((MethodDescriptor) null);
	}
	
	
	@Test
	public void testInvokeOnMissingClass() throws InvocationException
	{
		// espera falhar por causa de ClassNotFoundException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(ClassNotFoundException.class));
		
		// especifica um nome de classe inexistente
		MethodDescriptor md = this.createMethodDescriptor();
		md.setClassName("a.invalid.Class");
		
		InvokerService.getInstance().call(md, "TEST", 777);
	}
	
	
	@Test
	public void testInvokeOnInvalidClass() throws Exception
	{
		// espera falhar por causa de ClassLoaderException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(ClassLoaderException.class));
		
		// especifica uma classe com construtor privado
		MethodDescriptor md = this.createMethodDescriptor();
		md.setClassName(InvokerInvalidClass.class.getCanonicalName());
		
		InvokerService.getInstance().call(md, "TEST", 777);
	}
	
	
	@Test
	public void testInvokeOnMissingMethod() throws Exception
	{
		// espera falhar por causa de IllegalArgumentException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));
		
		// especifica um metodo inexistente
		MethodDescriptor md = this.createMethodDescriptor();
		md.setName("missingMethod");
		
		InvokerService.getInstance().call(md, "TEST", 777);
	}
	
	
	@Test
	public void testInvokeOnInvalidMethod() throws Exception
	{
		// espera falhar por causa de IllegalArgumentException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));
		
		// especifica um metodo privado
		MethodDescriptor md = this.createMethodDescriptor();
		md.setName("privateMethod");
		
		//InvokerService.getInstance().call(md, "TEST", 777);
		InvokerService.getInstance().invoke(md.getClassName(), md.getName(), Object.class, "TEST",777);
	}

	
	@Test
	public void testInvokeWithInvalidParameterTypes() throws Exception
	{
		// espera falhar por causa de IllegalArgumentException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));
		
		// especifica os parametros na ordem invertida
		MethodDescriptor md = this.createMethodDescriptor();
		md.getParameters().forEach(pd -> pd.setIndex(pd.getIndex() == 0 ? 1 : 0));

		InvokerService.getInstance().call(md, "TEST", 777);
	}
	
	
	
	@Test
	public void testInvokeWithInvalidParameterValues() throws Exception
	{
		// espera falhar por causa de IllegalArgumentException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));
		
		MethodDescriptor md = this.createMethodDescriptor();

		// inverte a ordem dos valores
		InvokerService.getInstance().call(md, 777, "TEST");
	}

	
	@Test
	public void testInvokeWithFewerParameterValues() throws Exception
	{
		// espera falhar por causa de IllegalArgumentException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));
		
		MethodDescriptor md = this.createMethodDescriptor();

		// especifica somente o valor do primeiro parametro
		InvokerService.getInstance().call(md, "TESTE");
	}
	
	
	@Test
	public void testInvokeWithMoreParameterValues() throws Exception
	{
		// espera falhar por causa de IllegalArgumentException
		thrown.expect(InvocationException.class);
		thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));
		
		MethodDescriptor md = this.createMethodDescriptor();

		// especifica valor a mais de parametros
		InvokerService.getInstance().call(md, "TESTE", 777, new Object());
	}
	

	@Test
	public void testInvokeValidMethod() throws Exception
	{	
		TestUtils.createJar("InvokerValidClass.jar", InvokerValidClass.class);
		String jarPath = TestUtils.pathFromTestDir("InvokerValidClass.jar");
		ClassLoaderService.getInstance().loadService(jarPath);
		
		MethodDescriptor md = this.createMethodDescriptor();
		//Object result = InvokerService.getInstance().call(md, "TESTE", 777);
		Object result = InvokerService.getInstance().invoke(md.getClassName(), md.getName(), Object.class, "TESTE",777);
		
		Assert.assertEquals("TESTE777", result);
	}	
}