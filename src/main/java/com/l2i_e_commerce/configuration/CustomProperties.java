package com.l2i_e_commerce.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.l2i_e_commerce")
public class CustomProperties {

	private String apiUrl;

}