package org.esfinge.virtuallab.services;

import java.util.Arrays;
import org.esfinge.virtuallab.descriptors.MethodDescriptor;
import org.esfinge.virtuallab.descriptors.ParameterDescriptor;
import org.esfinge.virtuallab.exceptions.ClassLoaderException;
import org.esfinge.virtuallab.exceptions.InvocationException;
import org.esfinge.virtuallab.services.invoker.InvokerInvalidClass;
import org.esfinge.virtuallab.services.invoker.InvokerValidClass;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Testes unitarios para a classe InvokerService.
 */
public class InvokerServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // gera o MethodDescriptor do metodo valido da classe de teste valida
    private MethodDescriptor createMethodDescriptor() {
        var pd1 = new ParameterDescriptor();
        pd1.setIndex(0);
        pd1.setName("param1");
        pd1.setDataType(String.class.getCanonicalName());

        var pd2 = new ParameterDescriptor();
        pd2.setIndex(1);
        pd2.setName("param2");
        pd2.setDataType(int.class.getCanonicalName());

        var md = new MethodDescriptor();
        md.setClassName(InvokerValidClass.class.getCanonicalName());
        md.setName("validMethod");
        md.setReturnType(String.class.getCanonicalName());
        md.setParameters(Arrays.asList(pd1, pd2));

        return md;
    }

    @Test(expected = InvocationException.class)
    public void testInvokeOnNull() throws InvocationException {
        InvokerService.getInstance().call((MethodDescriptor) null);
    }

    @Test
    public void testInvokeOnMissingClass() throws InvocationException {
        // espera falhar por causa de ClassNotFoundException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(ClassNotFoundException.class));

        // especifica um nome de classe inexistente
        var md = this.createMethodDescriptor();
        md.setClassName("a.invalid.Class");

        InvokerService.getInstance().call(md, "TEST", 777);
    }

    @Test
    public void testInvokeOnInvalidClass() throws Exception {
        // espera falhar por causa de IllegalAccessException
        //thrown.expect(InvocationException.class);
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(ClassLoaderException.class));

        // especifica uma classe com construtor privado
        var md = this.createMethodDescriptor();
        md.setClassName(InvokerInvalidClass.class.getCanonicalName());

        InvokerService.getInstance().call(md, "TEST", 777);
    }

    @Test
    public void testInvokeOnMissingMethod() throws Exception {
        // espera falhar por causa de IllegalArgumentException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));

        // especifica um metodo inexistente
        var md = this.createMethodDescriptor();
        md.setName("missingMethod");

        InvokerService.getInstance().call(md, "TEST", 777);
    }

    @Test
    public void testInvokeOnInvalidMethod() throws Exception {
        // espera falhar por causa de IllegalArgumentException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));

        // especifica um metodo privado
        var md = this.createMethodDescriptor();
        md.setName("privateMethod");

        InvokerService.getInstance().call(md, "TEST", 777);
    }

    @Test
    public void testInvokeWithInvalidParameterTypes() throws Exception {
        // espera falhar por causa de IllegalArgumentException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));

        // especifica os parametros na ordem invertida
        var md = this.createMethodDescriptor();
        md.getParameters().forEach(pd -> pd.setIndex(pd.getIndex() == 0 ? 1 : 0));

        InvokerService.getInstance().call(md, "TEST", 777);
    }

    @Ignore("Classe de servico nao carregada")
    @Test
    public void testInvokeWithInvalidParameterValues() throws Exception {
        // espera falhar por causa de IllegalArgumentException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));

        var md = this.createMethodDescriptor();

        // inverte a ordem dos valores
        InvokerService.getInstance().call(md, 777, "TEST");
    }

    @Ignore("Classe de servico nao carregada")
    @Test
    public void testInvokeWithFewerParameterValues() throws Exception {
        // espera falhar por causa de IllegalArgumentException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));

        var md = this.createMethodDescriptor();

        // especifica somente o valor do primeiro parametro
        InvokerService.getInstance().call(md, "TESTE");
    }

    @Ignore(value = "entrando no throwIfNull classLPADEREXEIOM")
    @Test
    public void testInvokeWithMoreParameterValues() throws Exception {
        // espera falhar por causa de IllegalArgumentException
        thrown.expect(InvocationException.class);
        thrown.expectCause(IsInstanceOf.instanceOf(IllegalArgumentException.class));

        var md = this.createMethodDescriptor();

        System.out.println(md);
        // especifica valor a mais de parametros
        //InvokerService.getInstance().call(md, "TESTE", 777);
        InvokerService.getInstance().call(md, "TESTE", 777);
    }

    @Ignore("Classe de servico nao carregada")
    @Test
    public void testInvokeValidMethod() throws Exception {
        var md = this.createMethodDescriptor();
        var result = InvokerService.getInstance().call(md, "TESTE", 777);
        Assert.assertEquals("TESTE777", result);
    }
}
