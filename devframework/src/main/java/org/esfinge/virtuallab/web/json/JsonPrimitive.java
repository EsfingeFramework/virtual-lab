package org.esfinge.virtuallab.web.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import org.esfinge.virtuallab.utils.JsonUtils;

/**
 * Classe representando tipos escalares JSON (string, number, boolean e null).
 *
 * @param <E>
 */
public class JsonPrimitive<E> extends JsonData {

    // armazena o elemento que sera transformado em JSON
    @JsonIgnore
    private E value;

    /**
     * Construtor padrao.
     */
    public JsonPrimitive() {

    }

    /**
     * Cria o primitivo JSON com o valor informado.
     *
     * @param value
     */
    @JsonCreator
    public JsonPrimitive(E value) {
        this.setValue(value);
    }

    /**
     * Atribui o valor do JSON.
     *
     * @param value
     */
    public void setValue(E value) {
        try {
            // transforma o valor para JSON
            var jsonNode = JsonUtils.fromObjectToJsonNode(value);

            // verifica se eh um primitivo valido
            if ((jsonNode != null) && !jsonNode.isValueNode()) {
                throw new Exception("Valor informado não é um primitivo JSON valido!");
            }

            // valor OK
            this.value = value;
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Retorna o valor do JSON.
     *
     * @return
     */
    @JsonValue
    public Object getValue() {
        return this.value;
    }

    /**
     * Retorna a representacao string desse primitivo JSON.
     */
    protected String toJsonString() {
        return JsonUtils.stringify(this.value);
    }

}
