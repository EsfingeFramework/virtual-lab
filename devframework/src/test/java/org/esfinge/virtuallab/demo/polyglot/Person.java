package org.esfinge.virtuallab.demo.polyglot;

import ef.qb.core.annotation.PersistenceType;
import ef.qb.core.annotation.PolyglotJoin;
import ef.qb.core.annotation.PolyglotOneToOne;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import org.bson.types.ObjectId;
import org.esfinge.virtuallab.polyglot.ObjectIdConverter;

@Entity
@PersistenceType(value = "JPA1", secondary = "MONGODB")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "personSequence")
    @SequenceGenerator(name = "personSequence", sequenceName = "person_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private Integer age;
    @Convert(converter = ObjectIdConverter.class)
    @Column(name = "address_id")
    private ObjectId addressId;
    @Transient
    @PolyglotOneToOne(referencedEntity = Address.class)
    @PolyglotJoin(name = "addressId", referencedAttributeName = "id")
    private Address address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ObjectId getAddressId() {
        return addressId;
    }

    public void setAddressId(ObjectId addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
