package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public interface ChatMapper {
    public void insertData(Chat chat);
    public String selectIDate();
    public String selectUDate();
    public void updateData(Chat chat);
    public void deleteData(Chat chat);
}