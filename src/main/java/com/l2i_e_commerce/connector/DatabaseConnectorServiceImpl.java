package com.l2i_e_commerce.connector;

import com.l2i_e_commerce.model.Item;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectorServiceImpl implements DatabaseConnectorService {

    @Autowired
    public DatabaseConnectorServiceImpl(MeiliSearchGenericService<Item> meiliSearchService) {
    }

    @Override
    public void fetchAndIndexItems() {
    }
}

