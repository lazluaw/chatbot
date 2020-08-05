package com.chatbot.web.mappers;

import com.chatbot.web.domains.ChatHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryMapper {
    public void insertData(ChatHistory chatHistory);
    public String selectLogin();
    public void updateData(ChatHistory chatHistory);
    public void deleteData(ChatHistory chatHistory);
}
