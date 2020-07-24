package com.chatbot.web.mappers;

import com.chatbot.web.domains.ChatHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryMapper {
    public void insertHistoryData(ChatHistory chatHistory);
    public void updateHistoryData(ChatHistory chatHistory);
    public void deleteHistoryData(ChatHistory chatHistory);
    public ChatHistory findOneHistoryData(String Id);
    public List<ChatHistory> findAllHistoryData();
}
