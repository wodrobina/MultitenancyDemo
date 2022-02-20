package eu.wodrobina.multitenancydemo.config.multitenancy;

public class TenantStore {

    private String tenantId;

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void clear() {
        this.tenantId = null;
    }
}
