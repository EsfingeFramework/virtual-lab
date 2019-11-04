package org.esfinge.virtuallab.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.exceptions.ClassLoaderException;
import org.esfinge.virtuallab.exceptions.InvocationException;
import org.esfinge.virtuallab.utils.Utils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;

/**
 * Testes unitarios para a classe ClassLoaderService.
 */
public class ClassLoaderServiceTest
{
	
	@Rule
	public ExpectedException thrown  = ExpectedException.none();
	

	private void createClass(String name, boolean valid, boolean service) throws IOException
	{
		// cria uma classe com a anotacao @ServiceClass
		IClassWriter mock = ClassMock.of(name);
		if(service)
			mock.annotation(ServiceClass.class);
		
		// adiciona um metodo valido/invalido com a anotacao @ServiceMethod
		mock.method("method").returnType(valid ? int.class : void.class).annotation(ServiceMethod.class);
		
		// salva o arquivo .CLASS da classe no diretorio de testes
		TestUtils.saveClassToTestDir((ClassMock) mock);
	}
	
	@Before
	public void doBefore()
	{
		// apaga o diretorio de testes antes de cada teste
		TestUtils.cleanTestDir();
	}

	@AfterClass
	public static void doAfterClass()
	{
		// apaga o diretorio de testes ao fim dos testes
		TestUtils.cleanTestDir();
	}

	@Test
	public void testLoadClassFromPath() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, true,true);
		String classPath = TestUtils.pathFromTestDir(className + ".class");
		System.out.println(classPath);
		
		// verifica se a classe foi carregada
		//Corrigida
		Class<?> clazz = ClassLoaderService.getInstance().loadService(new File(classPath));
		Assert.assertNotNull(clazz);
		Assert.assertEquals(className, clazz.getCanonicalName());
	}

	@Test
	public void testLoadClassFromStream() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, true,true);
		String classPath = TestUtils.pathFromTestDir(className + ".class");

		// verifica se a classe foi carregada
		ClassLoaderService.getInstance().loadService(new File(classPath));
		Class<?> clazz = ClassLoaderService.getInstance().getService(className);
		Assert.assertNotNull(clazz);
		Assert.assertEquals(className, clazz.getCanonicalName());
	}

	
	//@Ignore("ClassLoaderService.getInstance().loadService")
	@Test
	public void testLoadClassFromJarPath() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String className1 = TestUtils.createMockClassName();
		String className2 = TestUtils.createMockClassName();
		String className3 = TestUtils.createMockClassName();
		System.out.println(className1+className2+className3);
		
		this.createClass(className1, true,true);
		this.createClass(className2, true,true);
		this.createClass(className3, false,true);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("someJar.jar", className1));
		
		Assert.assertTrue(TestUtils.createJar("someJar2.jar", className2));
		
		Assert.assertTrue(TestUtils.createJar("someJar3.jar", className3));
		
		
		String jarPath = TestUtils.pathFromTestDir("someJar.jar");
		String jarPath2 = TestUtils.pathFromTestDir("someJar2.jar");
		String jarPath3 = TestUtils.pathFromTestDir("someJar3.jar");
		
		
		// assegura que as classes nao estao carregadas
		TestUtils.assertClassNotLoaded(className1);
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		// verifica se as classes foram carregadas
		ClassLoaderService.getInstance().loadService(jarPath);
		ClassLoaderService.getInstance().loadService(jarPath2);
		ClassLoaderService.getInstance().loadService(jarPath3);
		Class<?> clazz = ClassLoaderService.getInstance().getService(className1);
		Assert.assertEquals(className1, clazz.getCanonicalName());
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);
		
		clazz = ClassLoaderService.getInstance().getService(className2);
		Assert.assertEquals(className2, clazz.getCanonicalName());
		TestUtils.assertClassNotLoaded(className3);
		
		clazz = ClassLoaderService.getInstance().getService(className3);
		Assert.assertEquals(className3, clazz.getCanonicalName());
		
		assertTrue(ClassLoaderService.getInstance().isServiceLoaded(className1));
		
		
		ClassLoaderService.getInstance().unloadService(jarPath);
		ClassLoaderService.getInstance().unloadService(jarPath2);
		ClassLoaderService.getInstance().unloadService(jarPath3);
		TestUtils.assertClassNotLoaded(className1);
		TestUtils.assertClassNotLoaded(className2);
		TestUtils.assertClassNotLoaded(className3);

		
	}
	
	@Test
	public void invalidObject() throws Exception
	{
		
		thrown.expect(ClassLoaderException.class);
		
		TestUtils.assertTestDirIsEmpty();
		String className1 = TestUtils.createMockClassName();
		this.createClass(className1, true,false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("someJar.jar", className1));
		
		String jarPath = TestUtils.pathFromTestDir("someJar.jar");
		TestUtils.assertClassNotLoaded(className1);
		ClassLoaderService.getInstance().loadService(jarPath);
	}

	
	@Test
	public void findclass()
	{
		//vai ser testado o findclass.
		//não sei o classloader
	}
}