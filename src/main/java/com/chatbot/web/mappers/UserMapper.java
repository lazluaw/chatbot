package com.chatbot.web.mappers;

import com.chatbot.web.domains.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public User selectLoginList(int user);
    public User selectNameList(int user);
}