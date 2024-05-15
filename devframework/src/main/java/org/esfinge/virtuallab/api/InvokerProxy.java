package org.esfinge.virtuallab.api;

import org.esfinge.virtuallab.exceptions.InvocationException;

/**
 * Permite invocar metodos de outras classes de servico.
 */
public interface InvokerProxy {

    /**
     * Invoca um metodo de uma classe de servico.
     *
     * @param <E>
     * @param qualifiedClassName
     * @param paramValues
     * @param methodName
     * @param returnType
     * @return
     */
    public <E> E invoke(String qualifiedClassName, String methodName, Class<E> returnType, Object... paramValues) throws InvocationException;
}
