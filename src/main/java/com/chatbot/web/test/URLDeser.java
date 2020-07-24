package com.chatbot.web.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import java.util.Map;

@Service
public class URLDeser {
    @Autowired
    Product product;
    public void serializer(Map<String, Object> jsonData) {
        ObjectMapper mapper = new ObjectMapper();
    }
    public Map<String, Object> testSerializer() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapData = mapper.readValue(new URL("https://api.bestbuy.com/beta/products/openBox(categoryId=abcat0400000)?apiKey=DQQ92FvXaeYdTJI9wPTUl0Lo"),
                new TypeReference<Map<String, Object>>(){});
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapData.get("metadata")));
        return mapData;
    }
}