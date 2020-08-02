package com.chatbot.web.mappers;

import com.chatbot.web.domains.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    public List<User> selectList();
}
