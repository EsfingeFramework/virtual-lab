package org.esfinge.virtuallab.services;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.exceptions.ValidationException;
import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.utils.Utils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.api.IClassWriter;

/**
 * Testes unitarios para a classe ValidationService.
 */
public class ValidationServiceTest
{
	private void createClass(String name, boolean valid) throws IOException
	{
		// cria uma classe com a anotacao @ServiceClass
		IClassWriter mock = ClassMock.of(name);
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
	public void testValidClassFromPath() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, true);
		String classPath = TestUtils.pathFromTestDir(className + ".class");

		// verifica se a classe eh valida
		ClassMetadata clazz = ValidationService.getInstance().validateUploadedFile(new File(classPath));
		Assert.assertEquals(className, clazz.getClassName());
	}

	/*Não está mais fazendo
	@Test
	public void testValidClassFromStream() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, true);
		InputStream classStream = TestUtils.streamFromTestDir(className + ".class");

		// verifica se a classe eh valida
		Class<?> clazz = ValidationService.getInstance().checkClassFile(classStream, className);
		Assert.assertEquals(className, clazz.getCanonicalName());
	}*/

	
	
	@Test(expected = ValidationException.class)
	public void testInvalidClassFromPath() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, false);
		String classPath = TestUtils.pathFromTestDir(className + ".class");

		// verifica se a classe eh valida
		ClassMetadata clazz = ValidationService.getInstance().validateUploadedFile(new File(classPath));
		
		
		Assert.assertNull(clazz.getClass());
	}
	/*Não está mais fazendo
	@Test
	public void testInvalidClassFromStream() throws Exception
	{
		// cria um nome para a classe de teste
		String className = TestUtils.createMockClassName();
		
		// assegura que a classe nao esta carregada
		TestUtils.assertClassNotLoaded(className);
		
		// cria a classe de teste
		this.createClass(className, false);
		InputStream classStream = TestUtils.streamFromTestDir(className + ".class");

		// verifica se a classe eh valida
		Class<?> clazz = ValidationService.getInstance().checkClassFile(classStream, className);
		Assert.assertNull(clazz);
	}*/

	
	@Test
	public void testValidJarFromPath() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String validClass1 = TestUtils.createMockClassName();
		
		this.createClass(validClass1, true);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("validJar.jar", validClass1));
		String jarPath = TestUtils.pathFromTestDir("validJar.jar");

		ClassMetadata classList = ValidationService.getInstance().validateUploadedFile(new File(jarPath));
		Assert.assertNotNull(classList);
		
	}

	/*
	@Test
	public void testValidJarFromStream() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String validClass = TestUtils.createMockClassName();
		String invalidClass1 = TestUtils.createMockClassName();
		String invalidClass2 = TestUtils.createMockClassName();
		
		this.createClass(validClass, true);
		this.createClass(invalidClass1, false);
		this.createClass(invalidClass2, false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("validJar.jar", validClass, invalidClass1, invalidClass2));
		InputStream jarStream = TestUtils.streamFromTestDir("validJar.jar");

		List<Class<?>> classList = ValidationService.getInstance().checkJarFile(jarStream, "validJar.jar");
		Assert.assertEquals(1, classList.size());
		Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getCanonicalName().equals(validClass)));
	}*/

	/*
	@Test
	public void testInvalidJarFromPath() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String invalidClass1 = TestUtils.createMockClassName();
		String invalidClass2 = TestUtils.createMockClassName();
		String invalidClass3 = TestUtils.createMockClassName();
		
		this.createClass(invalidClass1, false);
		this.createClass(invalidClass2, false);
		this.createClass(invalidClass3, false);

		// cria o jar
		Assert.assertTrue(TestUtils.createJar("invalidJar.jar", invalidClass1, invalidClass2, invalidClass3));
		String jarPath = TestUtils.pathFromTestDir("invalidJar.jar");

		List<Class<?>> classList = ValidationService.getInstance().checkJarFile(jarPath);
		Assert.assertNull(classList);
	}

	@Test
	public void testInvalidJarFromStream() throws Exception
	{
		TestUtils.assertTestDirIsEmpty();
		
		// cria umas classes de teste
		String invalidClass1 = TestUtils.createMockClassName();
		String invalidClass2 = TestUtils.createMockClassName();
		String invalidClass3 = TestUtils.createMockClassName();
		
		this.createClass(invalidClass1, false);
		this.createClass(invalidClass2, false);
		this.createClass(invalidClass3, false);
		
		// cria o jar
		Assert.assertTrue(TestUtils.createJar("invalidJar.jar", invalidClass1, invalidClass2, invalidClass3));
		InputStream jarStream = TestUtils.streamFromTestDir("invalidJar.jar");

		List<Class<?>> classList = ValidationService.getInstance().checkJarFile(jarStream, "invalidJar.jar");
		Assert.assertNull(classList);
	}*/
}
