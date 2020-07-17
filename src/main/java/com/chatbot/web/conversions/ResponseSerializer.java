package com.chatbot.web.conversions;

import com.chatbot.web.domains.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

public class ResponseSerializer extends JsonSerializer<Response> {
    @Override
    public void serialize(Response value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("version");
        gen.writeString(String.valueOf(value.getVersion()));

        gen.writeObject("template");
        gen.writeStartArray("outputs");
        gen.writeObject("simpleText");
        gen.writeFieldName("text");
        gen.writeString("text");

        gen.writeEndArray();
        gen.writeEndObject();
    }
}