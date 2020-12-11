package nl.wderoode.assesment.essentials;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class DatabaseMigrator {
    private final DataSource dataSource;

    public DatabaseMigrator(@Qualifier("postgresDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void migrateWithFlyWay() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("sql")
                .load();

        flyway.migrate();
    }
}
