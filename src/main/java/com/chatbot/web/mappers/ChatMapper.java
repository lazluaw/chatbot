package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMapper {
    public void insertChat(Chat chat);
    public void updateChat(Chat chat);
    public void deleteChat(Chat chat);
    public Chat selectChat(String chatIdx);
    public List<Chat> selectChats();
}