package org.esfinge.virtuallab.demo.polyglot;

import esfinge.querybuilder.core.Repository;
import esfinge.querybuilder.core.annotation.TargetEntity;
import java.util.List;
import org.esfinge.virtuallab.api.annotations.ServiceDAO;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.polyglot.PolyglotConfig;

@ServiceDAO(
        label = "PersonDAO",
        description = "Pesquisa pessoas com endereço.",
        url = "jdbc:postgresql://localhost:5432/postgres",
        user = "postgres",
        password = "postgres",
        dialect = "org.hibernate.dialect.PostgreSQLDialect"
)
@PolyglotConfig(
        secondaryType = "MONGODB",
        secondaryUrl = "mongodb://localhost:27017/testdb",
        secondaryUser = "",
        secondaryPassword = "",
        secondaryDialect = ""
)
@TargetEntity(Person.class)
public interface PolyglotExample extends Repository<Person> {

    @ServiceMethod(
            label = "Lista completa.",
            description = "Lista completa de pessoas com endereço."
    )
    //@TableReturn(fields = "{id, name, lastName, age, address}",
    //       headerLabels = "{Id, Nome, Sobrenome, Idade, Endereço}")
    @TableReturn
    List<Person> getPerson();
}
