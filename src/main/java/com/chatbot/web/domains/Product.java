package com.chatbot.web.domains;

import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.List;

@Component @Lazy
@Getter @Setter @ToString
public class Product {
    private String sku;
    private String version;
    private String text;
    private Object simpleText, template;
    private List<Product> list, outputs;
}