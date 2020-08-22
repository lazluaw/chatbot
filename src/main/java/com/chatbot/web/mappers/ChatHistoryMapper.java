package com.chatbot.web.mappers;

import com.chatbot.web.domains.ChatHistory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChatHistoryMapper {
    public void insertData(ChatHistory chatHistory);
    public void updateData(ChatHistory chatHistory);
    public void deleteData(ChatHistory chatHistory);
}