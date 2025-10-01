package com.ProjectHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
public class RsaKeyConverterConfig {

    // Adiciona um conversor para a chave privada no formato "BEGIN RSA PRIVATE KEY"
    @Bean
    public Converter<String, RSAPrivateKey> rsaPrivateKeyConverter() {
        return new Converter<String, RSAPrivateKey>() {
            @Override
            public RSAPrivateKey convert(String key) {
                try {
                    String cleanKey = key
                            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                            .replace("-----END RSA PRIVATE KEY-----", "")
                            .replaceAll("\\s", "")
                            .replaceAll("\\n", "");// Remove quebras de linha (incluindo as de \n)

                    // A chave deve ser decodificada em Base64
                    byte[] keyBytes = Base64.getDecoder().decode(cleanKey);

                    // Nota: O formato "BEGIN RSA PRIVATE KEY" é tipicamente X.509/PKCS#1,
                    // mas o Spring Security espera PKCS#8.
                    // Para simplificar, esta solução customizada pode não funcionar
                    // corretamente se o formato for estritamente PKCS#1.
                    // **O MELHOR É FAZER A CONVERSÃO COM OPENSSL (Opção 1)**.

                    // Tentativa de usar PKCS#8 após limpar:
                    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                    KeyFactory kf = KeyFactory.getInstance("RSA");
                    return (RSAPrivateKey) kf.generatePrivate(spec);
                } catch (Exception e) {
                    throw new RuntimeException("Falha ao converter a chave RSA Privada: " + e.getMessage(), e);
                }
            }
        };
    }
}