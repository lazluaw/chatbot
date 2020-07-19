package com.chatbot.web.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component @Getter @Setter
@NoArgsConstructor
public class ResponseOutput {
    private Object simpleText;
    private String text = "text";
}
