package org.n52.oidcspring.authentication;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@Configuration
public class CustomJwkTokenStoreConfiguration {
    private final ResourceServerProperties resource;

    public CustomJwkTokenStoreConfiguration(ResourceServerProperties resource) {
        this.resource = resource;
    }

    @Bean
    public TokenStore jwkTokenStore() {
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return new JwkTokenStore(this.resource.getJwk().getKeySetUri(), tokenConverter);
    }
}