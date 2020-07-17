package com.chatbot.web.conversions;

import com.chatbot.web.domains.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class Output {
    @Autowired
    Product product;
    private ObjectMapper mapper;
    private String jsonStr;
    private JSONObject jsonObject;
    public void deserializer() {
        jsonObject = new JSONObject();
        mapper = new ObjectMapper();
        try {
            jsonObject.put("version", product.getVersion());
            jsonObject.put("text", product.getText());
            jsonObject.put("?", product.getList());
            jsonStr = mapper.writeValueAsString(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return jsonStr;
    }
}