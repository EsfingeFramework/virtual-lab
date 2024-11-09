package org.esfinge.virtuallab.demo.polyglot;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import ef.qb.core.annotation.PersistenceType;
import java.io.Serializable;
import org.bson.types.ObjectId;

@Entity
@PersistenceType("MONGODB")
public class Address implements Serializable {

    @Id
    private ObjectId id;
    private String city;
    private String uf;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
