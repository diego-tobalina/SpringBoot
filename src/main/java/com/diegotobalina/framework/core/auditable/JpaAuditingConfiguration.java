package com.diegotobalina.framework.core.auditable;

import com.diegotobalina.framework.core.security.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    public UserDetailsService userDetailsService;

    public JpaAuditingConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl(this.userDetailsService);
    }
}
