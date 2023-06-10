package com.l2i_e_commerce.configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rsa.key")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}