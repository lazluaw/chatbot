package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Input;
import com.chatbot.web.conversions.Output;
import com.chatbot.web.domains.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ProductController {
    @Autowired
    Output output;
    @Autowired
    Input input;
    @Autowired
    Product product;
    @ResponseBody
    @PostMapping("/product")
    public void test(@RequestBody Map<String, Object> jsonData) {
        System.out.println(jsonData);
        input.serializer(jsonData);
//        return output.deserializer();
    }
    @GetMapping("/api")
    public Map<String, Object> productInput() throws IOException {
        return input.testSerializer();
    }
}