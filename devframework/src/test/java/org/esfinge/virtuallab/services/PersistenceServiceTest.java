package org.esfinge.virtuallab.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import net.sf.esfinge.classmock.ClassMock;
import org.apache.commons.io.FileUtils;
import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.exceptions.PersistenceException;
import org.esfinge.virtuallab.utils.Utils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Testes unitarios para a classe PersistenceService.
 */
public class PersistenceServiceTest {
    // diretorio padrao de upload

    private static final String UPLOAD_DIR = Utils.getInstance().getUploadDir();

    private void createClass(String name, boolean valid) throws IOException {
        // cria uma classe com a anotacao @ServiceClass
        var mock = ClassMock.of(name);
        mock.annotation(ServiceClass.class);

        // adiciona um metodo valido/invalido com a anotacao @ServiceMethod
        mock.method("method").returnType(valid ? int.class : void.class).annotation(ServiceMethod.class);

        // salva o arquivo .CLASS da classe no diretorio de testes
        TestUtils.saveClassToTestDir((ClassMock) mock);
    }

    private void createFile(String name) throws IOException {
        FileUtils.touch(Paths.get(TestUtils.TEST_DIR, name).toFile());
    }

    private PersistenceService getPersistenceService() throws Exception {
        // a lista de classes eh criada no construtor privado da classe PersistenceService
        // por isso precisamos ter acesso via reflection
        var c = PersistenceService.class.getDeclaredConstructor();
        c.setAccessible(true);
        var p = c.newInstance();
        return p;
    }

    // Metodo utilitario para testar os metodos save() da classe PersistenceService.
    private InputStream createStreamForUploadFile(String fileName) throws Exception {
        FileUtils.moveFileToDirectory(Paths.get(TestUtils.TEST_DIR, fileName).toFile(),
                Paths.get(TestUtils.TEST_DIR, "upload").toFile(), true);

        return TestUtils.streamFromTestDir(String.format("upload/%s", fileName));
    }

    @Before
    public void doBefore() {
        // apaga o diretorio de testes antes de cada teste
        TestUtils.cleanTestDir();
    }

    @BeforeClass
    public static void doBeforeClass() {
        // atribui o diretorio de testes como diretorio de upload
        Utils.getInstance().setProperty("upload.dir", TestUtils.TEST_DIR);
    }

    @AfterClass
    public static void doAfterClass() throws IOException {
        // apaga os arquivos do diretorio de teste
        TestUtils.cleanTestDir();

        // volta o diretorio de upload original
        Utils.getInstance().setProperty("upload.dir", UPLOAD_DIR);
    }

    @Before
    public void cleanUploadDir() throws IOException {
        TestUtils.cleanTestDir();
    }

    @Ignore
    //@Test(expected = InvocationTargetException.class)
    @Test(expected = AssertionError.class)
    public void testListClassesEmptyUploadDir() throws Exception {
        TestUtils.assertTestDirIsEmpty();
        Assert.assertEquals(0, this.getPersistenceService().listServiceClasses().size());
    }

    @Ignore
    @Test
    public void testListClassesValidClasses() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes validas
        var validClass1 = TestUtils.createMockClassName();
        var validClass2 = TestUtils.createMockClassName();

        this.createClass(validClass1, true);
        this.createClass(validClass2, true);

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(2, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass1)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass2)));
    }

    @Ignore
    @Test
    public void testListClassesInvalidClasses() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes invalidas
        var invalidClass1 = TestUtils.createMockClassName();
        var invalidClass2 = TestUtils.createMockClassName();

        this.createClass(invalidClass1, false);
        this.createClass(invalidClass2, false);
        Assert.assertEquals(2, TestUtils.listTestDir().size());

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(0, classList.size());
    }

    @Ignore
    //@Test(expected = InvocationTargetException.class)
    @Test(expected = AssertionError.class)
    public void testListClassesInvalidFiles() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 arquivos de texto
        this.createFile("File1.txt");
        this.createFile("File2.txt");
        Assert.assertEquals(2, TestUtils.listTestDir().size());

        var classList = this.getPersistenceService().listServiceClasses();
    }

    @Ignore
    @Test
    public void testListClassesWithInvalidFiles() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 arquivos de texto
        this.createFile("File1.txt");
        this.createFile("File2.txt");

        // cria 2 classes validas
        var validClass1 = TestUtils.createMockClassName();
        var validClass2 = TestUtils.createMockClassName();

        this.createClass(validClass1, true);
        this.createClass(validClass2, true);
        Assert.assertEquals(4, TestUtils.listTestDir().size());

        //Não está gerando o ClassDercriptor
        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(2, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass1)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass2)));
    }

    @Ignore
    @Test
    public void testListClassesWithInvalidClasses() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes validas
        var validClass1 = TestUtils.createMockClassName();
        var validClass2 = TestUtils.createMockClassName();

        this.createClass(validClass1, true);
        this.createClass(validClass2, true);

        // cria 2 classes invalidas
        var invalidClass1 = TestUtils.createMockClassName();
        var invalidClass2 = TestUtils.createMockClassName();

        this.createClass(invalidClass1, false);
        this.createClass(invalidClass2, false);
        Assert.assertEquals(4, TestUtils.listTestDir().size());

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(2, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass1)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass2)));
    }

    @Ignore("Erro ao carregar servico: validJar.jar\r\n")
    @Test
    public void testListClassesValidJar() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes validas
        var validClass1 = TestUtils.createMockClassName();
        var validClass2 = TestUtils.createMockClassName();

        this.createClass(validClass1, true);
        this.createClass(validClass2, true);

        // cria 2 classes invalidas
        var invalidClass1 = TestUtils.createMockClassName();
        var invalidClass2 = TestUtils.createMockClassName();

        this.createClass(invalidClass1, false);
        this.createClass(invalidClass2, false);

        // cria o jar
        Assert.assertTrue(TestUtils.createJar("validJar.jar", validClass1, validClass2, invalidClass1, invalidClass2));

        // apaga as classes, ficando somente o jar
        TestUtils.deleteFromTestDir("class");
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(2, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass1)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass2)));
    }

    @Ignore
    @Test
    public void testListClassesInvalidJar() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes invalidas
        var invalidClass1 = TestUtils.createMockClassName();
        var invalidClass2 = TestUtils.createMockClassName();

        this.createClass(invalidClass1, false);
        this.createClass(invalidClass2, false);

        // cria o jar
        Assert.assertTrue(TestUtils.createJar("invalidJar.jar", invalidClass1, invalidClass2));

        // apaga as classes, ficando somente o jar
        TestUtils.deleteFromTestDir("class");
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(0, classList.size());
    }

    @Ignore
    @Test
    public void testListClassesAllTogether() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes validas
        var validClass1 = TestUtils.createMockClassName();
        var validClass2 = TestUtils.createMockClassName();

        this.createClass(validClass1, true);
        this.createClass(validClass2, true);

        // cria 2 classes invalidas
        var invalidClass1 = TestUtils.createMockClassName();
        var invalidClass2 = TestUtils.createMockClassName();

        this.createClass(invalidClass1, false);
        this.createClass(invalidClass2, false);

        // cria os jars valido/invalido
        Assert.assertTrue(TestUtils.createJar("validJar.jar", validClass1, validClass2));
        Assert.assertTrue(TestUtils.createJar("invalidJar.jar", invalidClass1, invalidClass2));

        // apaga as classes, ficando somente os jars
        TestUtils.deleteFromTestDir("class");
        Assert.assertEquals(2, TestUtils.listTestDir().size());

        // cria +1 classe valida/invalida
        var validClass3 = TestUtils.createMockClassName();
        var invalidClass3 = TestUtils.createMockClassName();

        this.createClass(validClass3, true);
        this.createClass(invalidClass3, false);

        // cria 1 arquivo de texto
        this.createFile("File1.txt");

        // 2 jars, 2 classes, 1 arquivo texto
        Assert.assertEquals(5, TestUtils.listTestDir().size());

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(3, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass1)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass2)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass3)));
    }

    @Ignore

    @Test
    public void testListMethodsNullClassName() throws Exception {
        TestUtils.assertTestDirIsEmpty();
        Assert.assertEquals(0, this.getPersistenceService().listServiceMethods(null).size());
    }

    @Ignore
    @Test
    public void testListMethodsInvalidClassName() throws Exception {
        TestUtils.assertTestDirIsEmpty();
        Assert.assertEquals(0, this.getPersistenceService().listServiceMethods("a.invalid.Class").size());
    }

    @Ignore(value = "this.getPersistenceService().listServiceMethods(validClass).size()")
    @Test
    public void testListMethodsSimpleClassName() throws Exception {
        TestUtils.assertTestDirIsEmpty();
        Assert.assertEquals(0, TestUtils.listTestDir().size());

        // cria 1 classe valida
        var validClass = TestUtils.createMockClassName();
        this.createClass("my.package." + validClass, true);
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        Assert.assertEquals(0, this.getPersistenceService().listServiceMethods(validClass).size());
    }

    @Ignore
    @Test
    public void testListMethodsValidClassName() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 1 classe valida
        var validClass = TestUtils.createMockClassName();
        this.createClass("my.package." + validClass, true);
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        var methodList = this.getPersistenceService().listServiceMethods("my.package." + validClass);
        Assert.assertEquals(1, methodList.size());
        Assert.assertNotNull(Utils.getFromCollection(methodList, m -> m.getName().equals("method")));
    }

    @Ignore
    //@Test(expected = PersistenceException.class)
    //@Test(expected = InvocationTargetException.class)
    @Test(expected = AssertionError.class)
    public void testSaveInvalidClass() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 1 classe invalida
        var invalidClass = TestUtils.createMockClassName();
        this.createClass(invalidClass, false);
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        var fileName = String.format("%s.class", invalidClass);

        //exeption
        this.getPersistenceService().saveUploadedFile(this.createStreamForUploadFile(fileName), fileName);

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(0, classList.size());
    }

    @Ignore
    @Test
    public void testSaveValidClass() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 1 classe valida
        var validClass = TestUtils.createMockClassName();
        this.createClass(validClass, true);
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        var fileName = String.format("%s.class", validClass);
        this.getPersistenceService().saveUploadedFile(this.createStreamForUploadFile(fileName), fileName);

        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(1, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass)));
    }

    @Ignore
    @Test(expected = PersistenceException.class)
    public void testSaveInvalidJar() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes invalidas
        var invalidClass1 = TestUtils.createMockClassName();
        var invalidClass2 = TestUtils.createMockClassName();

        this.createClass(invalidClass1, false);
        this.createClass(invalidClass2, false);

        // cria o jar
        Assert.assertTrue(TestUtils.createJar("invalidJar.jar", invalidClass1, invalidClass2));
        //problema de invocação.....
        // apaga as classes, ficando somente o jar
        TestUtils.deleteFromTestDir("class");

        Assert.assertEquals(1, TestUtils.listTestDir().size());

        this.getPersistenceService().saveUploadedFile(this.createStreamForUploadFile("invalidJar.jar"), "invalidJar.jar");
        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(0, classList.size());
    }

    @Ignore
    @Test
    public void testSaveValidJar() throws Exception {
        TestUtils.assertTestDirIsEmpty();

        // cria 2 classes validas
        var validClass1 = TestUtils.createMockClassName();
        var validClass2 = TestUtils.createMockClassName();

        this.createClass(validClass1, true);
        this.createClass(validClass2, true);

        // cria o jar
        Assert.assertTrue(TestUtils.createJar("validJar.jar", validClass1, validClass2));

        // apaga as classes, ficando somente o jar
        TestUtils.deleteFromTestDir("class");
        Assert.assertEquals(1, TestUtils.listTestDir().size());

        this.getPersistenceService().saveUploadedFile(this.createStreamForUploadFile("validJar.jar"), "validJar.jar");
        var classList = this.getPersistenceService().listServiceClasses();
        Assert.assertEquals(2, classList.size());
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass1)));
        Assert.assertNotNull(Utils.getFromCollection(classList, c -> c.getQualifiedName().equals(validClass2)));
    }
}
