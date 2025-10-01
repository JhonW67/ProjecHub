package com.ProjectHub.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jws-verification-key}")
    private RSAPublicKey publicKey;

    @Value("${spring.security.oauth2.authorizationserver.jwt.jws-signing-key}")
    private String privateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.
                        anyRequest().permitAll())

                .oauth2ResourceServer(oauth2 -> oauth2.
                        jwt(Customizer.withDefaults()))// Configuração para JWT
                .sessionManagement(session -> session.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable); // Remove form padrão
        return http.build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        RSAKey.Builder builder = new RSAKey.Builder(this.publicKey);
        builder.privateKey(privateKey);
        JWK jwk = builder.build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey() {
        try {
            // substitui "\n" literais por quebras de linha
            String formattedKey = privateKey.replace("\\n", "\n");

            // converte para RSAPrivateKey
            InputStream keyStream = new ByteArrayInputStream(formattedKey.getBytes(StandardCharsets.UTF_8));
            return RsaKeyConverters.pkcs8().convert(keyStream);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar chave privada", e);
        }
    }
}
