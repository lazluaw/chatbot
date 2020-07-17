package com.chatbot.web.conversions;

import com.chatbot.web.domains.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ResponseSerializer extends JsonSerializer {
    @Autowired
    Response response;
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        gen.writeStartObject();

        gen.writeFieldName("version");
        gen.writeString(String.valueOf(response.getVersion()));

        gen.writeFieldName("template");
        gen.writeObject(response.getTemplate());

        gen.writeArrayFieldStart("outputs");

        gen.writeFieldName("simpleText");
        gen.writeObject(response.getSimpleText());

        gen.writeFieldName("text");
        gen.writeString(String.valueOf(response.getText()));

        gen.writeEndArray();
        gen.writeEndObject();
    }
}