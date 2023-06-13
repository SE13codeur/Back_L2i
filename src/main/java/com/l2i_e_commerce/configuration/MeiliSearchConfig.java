/*
package com.l2i_e_commerce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meilisearch.sdk.*;


@Configuration
public class MeiliSearchConfig {

    @Value("${meilisearch.indexUid}")
    private String meiliSearchIndexUid;

    @Bean
    public String meiliSearchIndexUid() {
        return meiliSearchIndexUid;
    }
    
    @Value("${meilisearch.url}")
    private String meiliSearchUrl;

    @Value("${meilisearch.apikey}")
    private String meiliSearchApiKey;

    @Bean
    public Client meiliSearchClient() {
        Config config = new Config(meiliSearchUrl, meiliSearchApiKey);
        return new Client(config);
    }
}
*/





