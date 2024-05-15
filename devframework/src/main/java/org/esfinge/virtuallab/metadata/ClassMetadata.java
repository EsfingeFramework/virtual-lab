package org.esfinge.virtuallab.metadata;

/**
 * Informacoes de metadados das classes.
 */
public interface ClassMetadata {

    /**
     * Retorna se eh uma classe de servico.
     *
     * @return
     */
    public default boolean isServiceClass() {
        return false;
    }

    /**
     * Retorna se eh uma classe de acesso a Banco de Dados.
     *
     * @return
     */
    public default boolean isServiceDAO() {
        return false;
    }

    /**
     * Retorna o rotulo para a classe.
     *
     * @return
     */
    public default String getLabel() {
        return "";
    }

    /**
     * Retorna o texto informativo sobre a classe.
     *
     * @return
     */
    public default String getDescription() {
        return "";
    }

    /**
     * Retorna o objeto Class da classe.
     *
     * @return
     */
    public Class<?> getClazz();

    /**
     * Retorna o nome da classe.
     *
     * @return
     */
    public String getClassName();
}
