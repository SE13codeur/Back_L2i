package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Item;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class MeiliSearchGenericServiceImpl<T extends Item> implements MeiliSearchGenericService<T> {

    private final Index meiliSearchIndex;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MeiliSearchGenericServiceImpl(Client client, String indexUid) throws Exception {
        meiliSearchIndex = client.index(indexUid);
    }

    @Override
    public void index(List<Item> items) throws Exception {
        String jsonItems = objectMapper.writeValueAsString(items);
        meiliSearchIndex.addDocuments(jsonItems);
    }

    @Override
    public List<T> findAllMeilisearch() throws Exception {
        String searchResultJson = meiliSearchIndex.rawSearch("");
        JsonNode searchResultNode = objectMapper.readTree(searchResultJson);
        JsonNode hitsNode = searchResultNode.get("hits");
        List<T> items = objectMapper.convertValue(hitsNode, new TypeReference<List<T>>() {});
        return items;
    }
    
    @Override
    public Optional<T> findById(String id) throws Exception {
    	String jsonResponse = meiliSearchIndex.getDocument(id);
    	if (jsonResponse == null) {
    		return Optional.empty();
    	}
    	T item = objectMapper.readValue(jsonResponse, new TypeReference<T>() {});
    	return Optional.of(item);
    }

    @Override
    public T save(T item) throws Exception {
        String jsonDocument = objectMapper.writeValueAsString(item);
        String jsonResponse = meiliSearchIndex.addDocuments(jsonDocument);
        return objectMapper.readValue(jsonResponse, new TypeReference<T>() {});
    }

    @Override
    public T update(T item) throws Exception {
        String jsonDocument = objectMapper.writeValueAsString(item);
        String jsonResponse = meiliSearchIndex.updateDocuments(jsonDocument);
        return objectMapper.readValue(jsonResponse, new TypeReference<T>() {});
    }

    @Override
    public void deleteById(String id) throws Exception {
        meiliSearchIndex.deleteDocument(id);
    }
}
