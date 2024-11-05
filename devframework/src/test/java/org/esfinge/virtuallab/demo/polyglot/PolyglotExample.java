package org.esfinge.virtuallab.demo.polyglot;

import ef.qb.core.Repository;
import ef.qb.core.annotation.TargetEntity;
import static ef.qb.core.utils.PersistenceTypeConstants.MONGODB;
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
        secondaryType = MONGODB,
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
    @TableReturn(fields = {"name", "lastName", "age", "address"},
            headerLabels = {"Nome", "Sobrenome", "Idade", "Endereço"})
    List<Person> getPerson();
}
