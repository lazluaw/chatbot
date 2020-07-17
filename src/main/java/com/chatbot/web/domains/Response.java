package com.chatbot.web.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter @Setter @ToString
@Component
public class Response {
    private String version;
    private String text;
}