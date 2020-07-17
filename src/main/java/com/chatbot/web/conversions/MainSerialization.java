package com.chatbot.web.conversions;

import com.chatbot.web.domains.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainSerialization {
    @Autowired Response response;
    public static void getResponseSerialization() throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        Response responses = getResponse();
        String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responses);
        System.out.println(response);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();

        simpleModule.addSerializer(Response.class, new ResponseSerializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }

    private static Response getResponse() {
        Response addContent = new Response(
                "version", "기본 텍스트"
        );
        return addContent;
    }
}
