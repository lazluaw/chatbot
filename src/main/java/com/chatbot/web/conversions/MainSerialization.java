package com.chatbot.web.conversions;

import com.chatbot.web.domains.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;

public class MainSerialization {

    public static String getResponseSerialization() throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        Map<String, String> responses = getContent();
        String responseStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responses);
        System.out.println(responseStr);
        return responseStr;
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Response.class, new ResponseSerializer());
        mapper.registerModule(simpleModule);
        System.out.println(mapper);
        return mapper;
    }

    private static Map<String, String> getContent() {
        Response response = new Response();
        response.setVersion("2.0");
        response.setText("기본 텍스트");
        Map<String, String> responses = new HashMap<>();
        responses.put("version", response.getVersion());
        responses.put("text", response.getText());
        return responses;
    }
}