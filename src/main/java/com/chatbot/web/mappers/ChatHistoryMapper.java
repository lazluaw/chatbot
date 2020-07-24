package com.chatbot.web.mappers;

import com.chatbot.web.domains.ChatHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryMapper {
    public void insertHistory(ChatHistory chatHistory);
    public void updateHistory(ChatHistory chatHistory);
    public void deleteHistory(ChatHistory chatHistory);
    public ChatHistory findOneHistory(String Id);
    public List<ChatHistory> findAllHistory();
}
