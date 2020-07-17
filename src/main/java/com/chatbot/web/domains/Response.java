package com.chatbot.web.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Getter @Setter @ToString
@Lazy @Component
public class Response {
    private String version;
    private Object template, simpleText;
    private String text;


    public Response(String version, String text) {
        this.version = version;
        this.text = text;
    }
}
