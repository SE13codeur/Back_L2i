package com.l2i_e_commerce.configuration;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import com.meilisearch.sdk.*;

import lombok.Data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MeiliSearchConfig {

    @Bean
    public Client meiliSearchClient() {
        Config config = new Config("http://your-meilisearch-instance-url:7700", "your-master-key");
        return new Client(config);
    }

    @Bean
    public MeiliSearchGenericService<Book> bookService(Client meiliSearchClient) throws Exception {
        return new MeiliSearchGenericServiceImpl<>(meiliSearchClient, "books");
    }
}


