package org.esfinge.virtuallab.descriptors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.esfinge.virtuallab.api.annotations.ParamAttribute;
import org.esfinge.virtuallab.metadata.ParameterMetadata;
import org.esfinge.virtuallab.utils.JsonUtils;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.json.JsonSchemaArray;
import org.esfinge.virtuallab.web.json.JsonSchemaElement;
import org.esfinge.virtuallab.web.json.JsonSchemaObject;

/**
 * Descritor de parametros de metodos.
 */
public class ParameterDescriptor implements Comparable<ParameterDescriptor> {
    // nome do parametro

    private String name;

    // label do parametro
    private String label;

    // tipo do parametro
    private String dataType;

    // se o parametro eh obrigatorio ou nao
    private boolean required;

    // posicao do parametro no metodo
    private int index;

    // formato JSON Schema para o parametro (utilizado pelo jsonform)
    private String jsonSchema;

    /**
     * Construtor padrao.
     */
    public ParameterDescriptor() {

    }

    /**
     * Construtor a partir dos metadados de uma classe.
     *
     * @param parameterMetadata
     */
    public ParameterDescriptor(ParameterMetadata parameterMetadata) {
        var paramType = parameterMetadata.getParameter().getType();

        this.name = parameterMetadata.getParameterName();
        this.dataType = paramType.getCanonicalName();
        this.required = parameterMetadata.isRequired();
        this.index = parameterMetadata.getIndex();

        // verifica se foi informado um label para o parametro
        this.label = Utils.isNullOrEmpty(parameterMetadata.getLabel()) ? this.name : parameterMetadata.getLabel();

        // recupera o schema JSON do parametro
        var schema = (JsonSchemaElement) JsonUtils.getJsonSchema(paramType);

        schema.setTitle(String.format("%s (%s)", this.label, this.dataType));
        schema.setRequired(this.required);

        // schema JSON dos campos do parametro (se houver)
        Map<String, ParamAttribute> fieldsMetadataMap = new HashMap<>();
        var fieldsMetadata = parameterMetadata.getFieldsMetadata();

        if (!Utils.isNullOrEmpty(fieldsMetadata)) {
            Arrays.asList(fieldsMetadata).forEach(p -> fieldsMetadataMap.put(p.name(), p));
        }

        for (var field : ReflectionUtils.getAllFields(paramType)) {
            try {
                // tenta obter o schema do campo
                var fieldSchema = this.findFieldSchema(schema, field.getName());

                if (fieldSchema != null) {
                    // tenta obter o metadado do campo
                    var fieldMetadata = fieldsMetadataMap.get(field.getName());
                    var fieldLabel = (fieldMetadata != null) && (fieldMetadata.label() != null) ? fieldMetadata.label() : field.getName();
                    var fieldDataType = field.getType().getCanonicalName();
                    fieldSchema.setTitle(String.format("%s (%s)", fieldLabel, fieldDataType));
                    fieldSchema.setRequired((fieldMetadata != null) ? fieldMetadata.required() : this.required);
                }
            } catch (Exception e) {
                e.printStackTrace();

                // TODO: debug..
                System.out.println(String.format("Erro ao processar campo [%s] do parametro [%s - %s]!", field.getName(), this.name, this.dataType));
            }
        }

        // gera a string do schema JSON
        this.jsonSchema = schema.toString();
    }

    /**
     * Tenta encontrar o schema do campo informado.
     */
    private JsonSchemaElement findFieldSchema(JsonSchemaElement schema, String name) {
        JsonSchemaObject schemaObj = null;

        // eh um objeto?
        if (schema instanceof JsonSchemaObject) {
            schemaObj = (JsonSchemaObject) schema;
        } // eh um array?
        else if (schema instanceof JsonSchemaArray) // array de objetos?
        {
            if (((JsonSchemaArray) schema).getItemsInfo() instanceof JsonSchemaObject) {
                schemaObj = (JsonSchemaObject) ((JsonSchemaArray) schema).getItemsInfo();
            }
        }

        if (schemaObj != null) {
            return (JsonSchemaElement) schemaObj.getPropertiesInfo().get(name);
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    @Override
    public int compareTo(ParameterDescriptor o) {
        return Integer.valueOf(this.index).compareTo(o.index);
    }
}
