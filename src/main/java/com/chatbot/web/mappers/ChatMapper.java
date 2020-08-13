package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMapper {
    public void insertData(Chat chat);
    public Chat selectList(int chat);
    public Chat selectDateList(int chat);
    public void updateData(Chat chat);
    public void deleteData(Chat chat);
}