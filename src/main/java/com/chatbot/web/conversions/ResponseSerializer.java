package com.chatbot.web.conversions;

import com.chatbot.web.domains.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ResponseSerializer extends JsonSerializer<Response> {
    @Override
    public void serialize(Response value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("version", value.getVersion());

        gen.writeObject("template");
        gen.writeStartArray("outputs");
        gen.writeObject("simpleText");
        gen.writeStringField("text", "기본 텍스트");

        gen.writeEndArray();
        gen.writeEndObject();
    }
}