package com.chatbot.web.conversions;

import com.chatbot.web.domains.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ChatParser {
    @Autowired
    Chat chat;
    public void dataParser(Map<String, Object> jsonParams) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject bot = (JSONObject) obj.get("bot");
            String chatKind = (String) bot.get("id");
            JSONObject userRequest = (JSONObject) obj.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            chat.setChatKind(chatKind);
            chat.setChatBody(chatBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}