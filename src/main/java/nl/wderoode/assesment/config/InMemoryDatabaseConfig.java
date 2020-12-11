package nl.wderoode.assesment.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class InMemoryDatabaseConfig {
    @Primary
    @Bean(name = "postgresDataSource")
    @SneakyThrows
    public DataSource dataSource() {
        return EmbeddedPostgres.builder().start().getPostgresDatabase();
    }

    @Bean("postgresNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
