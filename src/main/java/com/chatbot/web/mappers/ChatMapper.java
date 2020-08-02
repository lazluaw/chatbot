package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMapper {
    public void insertData(Chat chat);
    public List<Chat> selectList();
    public void updateData(Chat chat);
    public void deleteData(Chat chat);
}