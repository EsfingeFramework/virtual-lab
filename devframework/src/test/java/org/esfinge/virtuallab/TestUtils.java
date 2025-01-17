package org.esfinge.virtuallab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import net.sf.esfinge.classmock.ClassMock;
import net.sf.esfinge.classmock.parse.ParseASM;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Assert;

/**
 * Classe com metodos utilitarios para testes.
 */
public abstract class TestUtils {
    // diretorio de teste

    public static final String TEST_DIR;
    public static final File TEST_DIR_FILE;
    private static int counter = 0;

    static {
        var testDir = Paths.get("target", "test-dir").toAbsolutePath().toString();
        try {
            // tenta criar o diretorio de teste em "target/test-dir"
            FileUtils.forceMkdir(new File(testDir));
        } catch (IOException ioe) {
            testDir = Paths.get(FileUtils.getTempDirectoryPath(), "test-dir").toAbsolutePath().toString();

            // tenta criar em "diretorio temporario/test-dir"
            new File(testDir).mkdirs();
        } finally {
            TEST_DIR = testDir;
            TEST_DIR_FILE = new File(TEST_DIR);
        }
    }

    /**
     * Cria um arquivo jar no diretorio de teste com as classes especificadas.
     *
     * @param name
     * @param classes
     * @return
     */
    public static boolean createJar(String name, Class<?>... classes) {
        return createJar(name, Arrays.asList(classes).stream().map(c -> c.getCanonicalName()).toArray(String[]::new));
    }

    /**
     * Cria um arquivo jar no diretorio de teste com as classes especificadas.
     *
     * @param name
     * @param qualifiedClassNames
     * @return
     */
    public static boolean createJar(String name, String... qualifiedClassNames) {
        try {
            // cria o manifest do arquivo jar de teste
            var manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

            // adiciona as classes ao jar
            try ( // arquivo jar
                    var jarFile = new JarOutputStream(new FileOutputStream(TEST_DIR + "/" + name), manifest)) {
                // adiciona as classes ao jar
                for (var clazz : qualifiedClassNames) {
                    var classPath = clazz.replace(".", "/") + ".class";
                    jarFile.putNextEntry(new JarEntry(classPath));

                    // caminho para o arquivo da classe
                    classPath = Paths.get("target", "test-classes", classPath).toAbsolutePath().toString();
                    if (!Paths.get(classPath).toFile().exists()) {
                        classPath = pathFromTestDir(FilenameUtils.getBaseName(classPath) + ".class");
                    }

                    jarFile.write(FileUtils.readFileToByteArray(new File(classPath)));
                    jarFile.closeEntry();
                }
                // fecha o arquivo jar
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Apaga o conteudo do diretorio de teste.
     *
     * @return
     */
    public static boolean cleanTestDir() {
        try {
            FileUtils.cleanDirectory(TEST_DIR_FILE);
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * Apaga os arquivos do diretorio de teste que contenham as extensoes informadas.
     *
     * @param fileExtensions
     */
    public static void deleteFromTestDir(String... fileExtensions) {
        for (var file : FileUtils.listFiles(TEST_DIR_FILE, fileExtensions, false)) {
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * Retorna os arquivos contidos no diretorio de teste.
     *
     * @return
     */
    public static List<File> listTestDir() {
        return new ArrayList<>(FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null));
    }

    /**
     * Copia as classes especificadas para o diretorio de teste.
     *
     * @param classes
     * @return
     */
    public static boolean copyToTestDir(Class<?>... classes) {
        try {
            for (var clazz : classes) {
                var classPath = clazz.getName().replace(".", "/") + ".class";
                FileUtils.copyFileToDirectory(new File(clazz.getClassLoader().getResource(classPath).getFile()),
                        TEST_DIR_FILE);
            }

            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * Obtem o caminho para o recurso a partir do diretorio de teste.
     *
     * @param resourceName
     * @return
     */
    public static String pathFromTestDir(String resourceName) {
        // retorna o caminho a partir do diretorio de teste
        return TEST_DIR + File.separator + resourceName;
    }

    /**
     * Obtem o stream para o recurso a partir do diretorio de teste.
     *
     * @param resourceName
     * @return
     * @throws java.io.IOException
     */
    public static InputStream streamFromTestDir(String resourceName) throws IOException {
        // retorna um stream a partir do diretorio de teste
        return FileUtils.openInputStream(new File(pathFromTestDir(resourceName)));
    }

    /**
     * Cria o arquivo .CLASS da classe mock no diretorio de testes.
     *
     * @param mock
     * @throws java.io.IOException
     */
    public static void saveClassToTestDir(ClassMock mock) throws IOException {
        var parserASM = new ParseASM(mock);
        var filePath = pathFromTestDir(String.format("%s.class", mock.name()));
        FileUtils.writeByteArrayToFile(new File(filePath), parserASM.parse());
    }

    /**
     * Cria um nome unico para a classe de teste.
     *
     * @return
     */
    public static String createMockClassName() {
        return String.format("TestClass_%03d", counter++);
    }

    /**
     * Assegura que a classe de teste nao esta carregada.
     *
     * @param className
     */
    public static void assertClassNotLoaded(String className) {
        try {
            // tenta carregar a classe
            Class.forName(className);

            // nao eh para chegar aqui!
            Assert.fail(String.format("Classe '%s' já está carregada!", className));
        } catch (ClassNotFoundException cnfe) {
            // classe nao encontrada, ok
        }
    }

    /**
     * Assegura que o diretorio de testes esta vazio.
     */
    public static void assertTestDirIsEmpty() {
        Assert.assertEquals(0, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());
    }
}
