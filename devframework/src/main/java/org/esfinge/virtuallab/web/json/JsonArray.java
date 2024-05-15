package org.esfinge.virtuallab.web.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.esfinge.virtuallab.utils.JsonUtils;

/**
 * Classe representando arrays JSON.
 *
 * @param <E>
 */
public class JsonArray<E> extends JsonData {
    // armazena os elementos que serao transformados em JSON

    @JsonIgnore
    private List<E> jsonArray;

    /**
     * Construtor padrao.
     */
    public JsonArray() {
        this.jsonArray = new ArrayList<>();
    }

    /**
     * Cria o array JSON com os valores informados.
     *
     * @param values
     */
    @JsonCreator
    @SuppressWarnings("unchecked")
    public JsonArray(E... values) {
        this();
        this.add(values);
    }

    /**
     * Adiciona os elementos ao array JSON.
     *
     * @param values
     */
    public JsonArray(Collection<E> values) {
        this();
        this.jsonArray.addAll(values);
    }

    /**
     * Adiciona os elementos ao array JSON.
     *
     * @param values
     */
    @SuppressWarnings("unchecked")
    public void add(E... values) {
        this.jsonArray.addAll(Arrays.asList(values));
    }

    /**
     * Remove um elemento do array JSON.
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        return this.jsonArray.remove(index);
    }

    /**
     * Retorna um elemento o array JSON.
     *
     * @param index
     * @return
     */
    public E getAt(int index) {
        return this.jsonArray.get(index);
    }

    /**
     * Retorna os elementos do array JSON em forma de lista.
     *
     * @return
     */
    public List<E> toList() {
        return this.jsonArray;
    }

    /**
     * Retorna os elementos do array JSON em forma de array JAVA.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @JsonValue
    public E[] toArray() {
        return (E[]) this.jsonArray.toArray();
    }

    /**
     * Retorna a representacao string desse array JSON.
     */
    protected String toJsonString() {
        return JsonUtils.stringify(this.jsonArray);
    }
}
