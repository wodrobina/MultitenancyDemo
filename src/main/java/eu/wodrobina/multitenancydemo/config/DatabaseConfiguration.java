package eu.wodrobina.multitenancydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("eu.wodrobina.*")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
