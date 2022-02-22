package eu.wodrobina.multitenancydemo.config.data;

import javax.sql.DataSource;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class InitDatabase {

    public static final String CREATE_TODO_TABLE = "create sequence hibernate_sequence start with 3 increment by 4;" +
            "create table Todo (id bigint not null, completed boolean, title varchar(255), primary key (id));";


//    @Value("${multitenancy.dataSources.tenantId}")
//    private String[] firstTenant;

    private Map<String, DataSource> dataSources;

    public InitDatabase(@Qualifier("dataSources") Map<String, DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    @Bean
    public void initDatabases() throws SQLException {
        createTodoTableForAllTenants();
        setDataToTenant1();
    }

    private void createTodoTableForAllTenants() throws SQLException {
        for (DataSource dataSource : dataSources.values()) {
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                statement.execute(CREATE_TODO_TABLE);
                statement.close();

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
            } finally {
                connection.close();
            }
        }
    }

    private void setDataToTenant1() throws SQLException {
        Connection connection = dataSources.get("tenant1").getConnection();
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("insert into todo (id, completed, title) values (1, false, 'Wash dishes')");
            statement.execute("insert into todo (id, completed, title) values (2, true, 'Do workout')");
            statement.close();

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.close();
        }
    }
}
