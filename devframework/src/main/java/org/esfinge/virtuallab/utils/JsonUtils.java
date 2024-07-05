package org.esfinge.virtuallab.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import org.esfinge.virtuallab.web.json.JsonArray;
import org.esfinge.virtuallab.web.json.JsonData;
import org.esfinge.virtuallab.web.json.JsonDataException;
import org.esfinge.virtuallab.web.json.JsonObject;
import org.esfinge.virtuallab.web.json.JsonPrimitive;
import org.esfinge.virtuallab.web.json.JsonSchema;
import org.esfinge.virtuallab.web.json.JsonSchemaArray;
import org.esfinge.virtuallab.web.json.JsonSchemaElement;
import org.esfinge.virtuallab.web.json.JsonSchemaObject;

/**
 * Metodos utilitarios para manipular strings representando objetos JSON.
 */
public class JsonUtils {
    // objeto responsavel pelo mapeamento de dados JSON

    private static final ObjectMapper _JSON_MAPPER = new ObjectMapper();

    // objeto responsavel pelo mapeamento de schemas JSON
    private static final JsonSchemaGenerator _SCHEMA_MAPPER = new JsonSchemaGenerator(_JSON_MAPPER);

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        _JSON_MAPPER.registerModule(module);

        // utiliza os campos para serializar/deserializar (nao precisa de getters/setters)
        _JSON_MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    /**
     * Converte a string JSON para JsonNode.
     *
     * @param jsonString
     * @return
     */
    public static JsonNode fromStringToJsonNode(String jsonString) throws JsonDataException {
        try {
            return _JSON_MAPPER.readTree(jsonString);
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte o objeto para JsonNode.
     *
     * @param object
     * @return
     */
    public static JsonNode fromObjectToJsonNode(Object object) throws JsonDataException {
        try {
            return _JSON_MAPPER.valueToTree(object);
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte o objeto para JsonData.
     *
     * @param object
     * @return
     */
    public static JsonData fromObjectToJsonData(Object object) throws JsonDataException {
        return convertToJsonData(stringify(object));
    }

    /**
     * Converte a string JSON para a classe informada.
     *
     * @param jsonString
     * @param typeClass
     * @return
     */
    public static <E> E convertTo(String jsonString, Class<E> typeClass) throws JsonDataException {
        try {
            return _JSON_MAPPER.readValue(jsonString, typeClass);
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte a string JSON para um mapa.
     *
     * @param jsonString
     * @return
     */
    public static Map<String, String> convertToMap(String jsonString) throws JsonDataException {
        Map<String, String> map = new HashMap<>();

        // monta o mapa com as propriedades a partir do root node
        fromStringToJsonNode(jsonString).fieldNames().forEachRemaining((k) -> map.put(k, getProperty(jsonString, k)));

        //
        return map;
    }

    /**
     * Converte a string JSON para uma lista do tipo da classe informada.
     *
     * @param jsonString
     * @param typeClass
     * @return
     */
    public static <E> List<E> convertToList(String jsonString, Class<E> typeClass) throws JsonDataException {
        try {
            return _JSON_MAPPER.readValue(jsonString,
                    _JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, typeClass));
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte a string JSON para um array do tipo da classe informada.
     *
     * @param jsonString
     * @param typeClass
     * @return
     */
    public static <E> E[] convertToArray(String jsonString, Class<E> typeClass) throws JsonDataException {
        try {
            return _JSON_MAPPER.readValue(jsonString,
                    _JSON_MAPPER.getTypeFactory().constructArrayType(typeClass));
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte a string JSON para um objeto JsonData.
     *
     * @param jsonString
     * @return
     */
    public static JsonData convertToJsonData(String jsonString) throws JsonDataException {
        // obtem o JsonNode
        var jsonNode = fromStringToJsonNode(jsonString);

        // verifica qual o tipo de JSON representa
        if (jsonNode.isValueNode()) {
            return convertToJsonPrimitive(jsonString, Object.class);
        }

        if (jsonNode.isArray()) {
            return convertToJsonArray(jsonString, Object.class);
        }

        if (jsonNode.isObject()) {
            return convertToJsonObject(jsonString);
        }

        // tipo nao suportado
        throw new JsonDataException("Tipo nao suportado de dado JSON!");
    }

    /**
     * Converte a string JSON para um objeto JsonPrimitive do tipo da classe informada.
     *
     * @param jsonString
     * @param typeClass
     * @return
     */
    public static <E> JsonPrimitive<E> convertToJsonPrimitive(String jsonString, Class<E> typeClass) throws JsonDataException {
        try {
            return _JSON_MAPPER.readValue(jsonString,
                    _JSON_MAPPER.getTypeFactory().constructParametricType(JsonPrimitive.class, typeClass));
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte a string JSON para um objeto JsonArray do tipo da classe informada.
     *
     * @param jsonString
     * @param typeClass
     * @return
     */
    public static <E> JsonArray<E> convertToJsonArray(String jsonString, Class<E> typeClass) throws JsonDataException {
        try {
            return _JSON_MAPPER.readValue(jsonString,
                    _JSON_MAPPER.getTypeFactory().constructParametricType(JsonArray.class, typeClass));
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte a string JSON para um objeto JsonObject.
     *
     * @param jsonString
     * @return
     */
    public static JsonObject convertToJsonObject(String jsonString) throws JsonDataException {
        return convertTo(jsonString, JsonObject.class);
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON.
     *
     * @param jsonString
     * @param property
     * @return
     */
    public static String getProperty(String jsonString, String property) throws JsonDataException {
        var prop = fromStringToJsonNode(jsonString).get(property);

        // verifica se a propriedade eh um container (array ou outro JSON)
        if (prop.isContainerNode()) {
            // retorna a string no formato JSON
            return prop.toString();
        }

        // retorna o valor convertido em string
        return prop.asText();
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para mapa.
     *
     * @param jsonString
     * @param property
     * @return
     */
    public static Map<String, String> getPropertyAsMap(String jsonString, String property) throws JsonDataException {
        return convertToMap(getProperty(jsonString, property));
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para uma lista do tipo da classe informada.
     *
     * @param typeClass
     * @param property
     * @return
     */
    public static <E> List<E> getPropertyAsList(String jsonString, String property, Class<E> typeClass) throws JsonDataException {
        return convertToList(getProperty(jsonString, property), typeClass);
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para um array do tipo da classe informada.
     *
     * @param typeClass
     * @param property
     * @return
     */
    public static <E> E[] getPropertyAsArray(String jsonString, String property, Class<E> typeClass) throws JsonDataException {
        return convertToArray(getProperty(jsonString, property), typeClass);
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonData.
     *
     * @param jsonString
     * @param property
     * @return
     */
    public static JsonData getPropertyAsJsonData(String jsonString, String property) throws JsonDataException {
        return convertToJsonData(getProperty(jsonString, property));
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonPrimitive do tipo da
     * classe informada.
     *
     * @param typeClass
     * @param property
     * @return
     */
    public static <E> JsonPrimitive<E> getPropertyAsJsonPrimitive(String jsonString, String property, Class<E> typeClass) throws JsonDataException {
        return convertToJsonPrimitive(getProperty(jsonString, property), typeClass);
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para um objeto JsonArray do tipo da classe
     * informada.
     *
     * @param typeClass
     * @param property
     * @return
     */
    public static <E> JsonArray<E> getPropertyAsJsonArray(String jsonString, String property, Class<E> typeClass) throws JsonDataException {
        return convertToJsonArray(getProperty(jsonString, property), typeClass);
    }

    /**
     * Retorna o valor de uma propriedade continda na string JSON convertido para um objeto JsonObject.
     *
     * @param jsonString
     * @param property
     * @return
     */
    public static JsonObject getPropertyAsJsonObject(String jsonString, String property) throws JsonDataException {
        return convertToJsonObject(getProperty(jsonString, property));
    }

    /**
     * Retorna o valor de uma propriedade contida na string JSON convertido para a classe informada.
     *
     * @param typeClass
     * @param property
     * @return
     */
    public static <E> E getPropertyAs(String jsonString, String property, Class<E> typeClass) throws JsonDataException {
        try {
            return _JSON_MAPPER.readValue(getProperty(jsonString, property), typeClass);
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Converte um objeto para sua representacao JSON.
     *
     * @param obj
     * @return
     */
    public static String stringify(Object obj) throws JsonDataException {
        try {
            return _JSON_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new JsonDataException(e);
        }
    }

    /**
     * Retorna o schema JSON do tipo da classe informada.
     *
     * @param typeClass
     * @return
     */
    public static JsonSchema getJsonSchema(Class<?> typeClass) {
        try {
            // gera a string do schema JSON
            var schemaString = stringify(_SCHEMA_MAPPER.generateSchema(typeClass));
            // monta um mapa com as propriedades do schema gerado
            var schemaMap = convertToMap(schemaString);

            // verifica se a classe eh do tipo Temporal (Calendar, Timestamp, Date..)
//		if( ReflectionUtils.isTemporalType(typeClass) )
//			schemaMap.put("type", "datetime-local");
// monta os schemas dos tipos referenciados (definitions)
            Map<String, JsonSchema> refsMap = new HashMap<>();

            if (schemaMap.containsKey("definitions")) {
                var refsSchemaMap = convertToMap(schemaMap.get("definitions"));
                for (var name : refsSchemaMap.keySet()) {
                    refsMap.put(String.format("#/definitions/%s", name),
                            parseSchema(name, convertToMap(refsSchemaMap.get(name))));
                }
                // substitui os schemas referenciados dentro dos tipos referenciados
                for (var refSchema : refsMap.values()) {
                    replaceRefsSchema(refSchema, refsMap);
                }
            }

// monta o schema principal
            var jsonSchema = parseSchema(schemaMap.get("title"), schemaMap);

// substitui os schemas referenciados
            if (schemaMap.containsKey("definitions")) {
                replaceRefsSchema(jsonSchema, refsMap);
            }

            return jsonSchema;
        } catch (JsonMappingException ex) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retorna o schema JSON de um elemento.
     */
    private static JsonSchema parseSchema(String name, Map<String, String> schemaMap) {
        JsonSchemaElement schema;

        // verifica o tipo do elemento (type se elemento normal, $ref se fizer referencia a outro elemento)
        var type = Utils.isNullOrEmpty(schemaMap.get("type")) ? schemaMap.get("$ref") : schemaMap.get("type");

        switch (type) {
            case "object":
                schema = new JsonSchemaObject();
                break;
            case "array":
                schema = new JsonSchemaArray();
                break;
            default:
                schema = new JsonSchemaElement();
                break;
        }

        // propriedades basicas
        schema.setType(type);
        schema.setTitle(name);
        if (schemaMap.containsKey("format")) {
            schema.setFormat(schemaMap.get("format"));
        }

        // preenche os subitens (objeto: properties / array: items)
        if (type.equals("object")) {
            // obtem o schema das propriedades do objeto
            var propsMap = convertToMap(schemaMap.get("properties"));
            for (var propName : propsMap.keySet()) {
                ((JsonSchemaObject) schema).addPropertyInfo(propName,
                        parseSchema(propName, convertToMap(propsMap.get(propName))));
            }
        } else if (type.equals("array")) {
            // obtem o schema do tipo dos elementos do array
            var itemsInfo = parseSchema("", convertToMap(schemaMap.get("items")));
            ((JsonSchemaArray) schema).setItemsInfo(itemsInfo);
        }

        return schema;
    }

    /**
     * Substitui os schemas referenciados nos elementos.
     */
    private static void replaceRefsSchema(JsonSchema jsonSchema, Map<String, JsonSchema> refsMap) {
        // array
        if (jsonSchema instanceof JsonSchemaArray) {
            // cast para facilitar
            var arraySchema = (JsonSchemaArray) jsonSchema;
            var itemsInfo = (JsonSchemaElement) arraySchema.getItemsInfo();

            if (refsMap.containsKey(itemsInfo.getType())) {
                arraySchema.setItemsInfo(refsMap.get(itemsInfo.getType()));
            }
        } // objeto
        else if (jsonSchema instanceof JsonSchemaObject) {
            // cast para facilitar
            var objSchema = (JsonSchemaObject) jsonSchema;
            var propertiesInfo = objSchema.getPropertiesInfo();
            for (var propName : propertiesInfo.keySet()) {
                var propInfo = (JsonSchemaElement) propertiesInfo.get(propName);

                if (refsMap.containsKey(propInfo.getType())) {
                    propertiesInfo.put(propName, refsMap.get(propInfo.getType()));
                }
            }
        }
    }
}
