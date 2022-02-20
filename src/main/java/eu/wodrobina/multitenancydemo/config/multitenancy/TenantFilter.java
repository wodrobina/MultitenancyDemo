package eu.wodrobina.multitenancydemo.config.multitenancy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TenantFilter implements Filter {

    private static final String TENANT_HEADER_NAME = "X-TENANT-ID";

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantFilter.class);

    @Autowired
    private TenantStore tenantStore;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Thread had tenant data: {} from an old request", this.tenantStore.getTenantId());

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String tenantId = request.getHeader(TENANT_HEADER_NAME);
        try {
            this.tenantStore.setTenantId(tenantId);
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            // Otherwise when a previously used container thread is used, it will have the old tenant id set and
            // if for some reason this filter is skipped, tenantStore will hold an unreliable value
            this.tenantStore.clear();
        }
    }
}


