package org.esfinge.virtuallab.domain;

import ef.qb.core.Repository;
import ef.qb.core.annotation.LesserOrEquals;
import java.util.List;
import org.esfinge.virtuallab.api.annotations.ServiceDAO;

@ServiceDAO(label = "DAO Temperaturas",
        description = "Banco de Dados: PostgreSQL  -  Entidade: Temperatura",
        url = "jdbc:postgresql://localhost:5432/postgres", user = "postgres", password = "postgres", dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface TemperaturaService extends Repository<Temperatura> {

    public List<Temperatura> getTemperatura();

    public List<Temperatura> getTemperaturaByMes(String mes);

    public List<Temperatura> getTemperaturaByMaximaOrderByMaximaAsc(@LesserOrEquals double temp);

    public List<Temperatura> getTemperaturaByMinimaOrderByMinimaDesc(@LesserOrEquals double temp);
}
