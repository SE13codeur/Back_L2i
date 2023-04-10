package com.l2i_e_commerce.configuration;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import com.meilisearch.sdk.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeiliSearchConfig {

    @Value("${meilisearch.url}")
    private String meiliSearchUrl;

    @Value("${meilisearch.apikey}")
    private String meiliSearchApiKey;

    @Bean
    public Client meiliSearchClient() {
        Config config = new Config(meiliSearchUrl, meiliSearchApiKey);
        return new Client(config);
    }

    @Bean
    public MeiliSearchGenericService<Book> bookService(Client meiliSearchClient) throws Exception {
        return new MeiliSearchGenericServiceImpl<>(meiliSearchClient, "books");
    }
}





