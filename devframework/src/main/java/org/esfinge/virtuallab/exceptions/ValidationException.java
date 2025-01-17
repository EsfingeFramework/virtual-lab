package org.esfinge.virtuallab.exceptions;

/**
 * Excecao relacionada ao ValidationService.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 6395251397295069505L;

    /**
     * Cria uma nova excecao com a mensagem informada.
     *
     * @param msg
     */
    public ValidationException(String msg) {
        super(msg);
    }

    /**
     * Cria uma nova excecao com a mensagem e causa informadas.
     *
     * @param msg
     * @param cause
     */
    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
