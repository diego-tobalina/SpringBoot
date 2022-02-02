package com.diegotobalina.framework.core.auditable;

import com.diegotobalina.framework.core.security.AuthenticationImpl;
import com.diegotobalina.framework.core.security.AuthenticationManager;
import com.diegotobalina.framework.core.security.UserDetailsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {


    public UserDetailsService userDetailsService;

    public AuditorAwareImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        AuthenticationManager authenticationManager = new AuthenticationManager(userDetailsService);
        if (!authenticationManager.isAuthenticated()) {
            String anonymousIdentifier = authenticationManager.getAnonymousIdentifier();
            return Optional.of(anonymousIdentifier);
        }
        Authentication authenticated = authenticationManager.getAuthenticated();
        AuthenticationImpl customUserDetails = (AuthenticationImpl) authenticated;
        return Optional.of(customUserDetails.getUserId());
    }
}
