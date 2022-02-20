package eu.wodrobina.multitenancydemo.config.multitenancy;

import javax.sql.DataSource;
import java.util.Map;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    @Qualifier("dataSources")
    private Map<String, DataSource> dataSources;

    @Override
    protected DataSource selectAnyDataSource() {
        return this.dataSources.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return this.dataSources.get(tenantIdentifier);
    }
}
