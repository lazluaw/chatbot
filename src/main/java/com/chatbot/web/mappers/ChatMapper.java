package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMapper {
    public void insertChatData(Chat chat);
    public void updateChatData(Chat chat);
    public void deleteChatData(Chat chat);
    public Chat findOneChatData(String Id);
    public List<Chat> findAllChatData();
}