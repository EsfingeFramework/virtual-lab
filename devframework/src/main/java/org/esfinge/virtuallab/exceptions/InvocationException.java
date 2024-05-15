package org.esfinge.virtuallab.exceptions;

/**
 * Excecao relacionada ao InvokerService.
 */
public class InvocationException extends RuntimeException {

    private static final long serialVersionUID = -8561294521041466259L;

    /**
     * Cria uma nova excecao com a mensagem informada.
     *
     * @param msg
     */
    public InvocationException(String msg) {
        super(msg);
    }

    /**
     * Cria uma nova excecao com a mensagem e causa informadas.
     *
     * @param msg
     * @param cause
     */
    public InvocationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
