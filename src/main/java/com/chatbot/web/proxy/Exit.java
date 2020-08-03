package com.chatbot.web.proxy;

import com.chatbot.web.mappers.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component @Lazy
public class Exit {
    @Autowired ChatMapper chatMapper;
    public void exit(Map<String, Object> jsonParams) {
        if(chatMapper.selectUDate() == null) {
            System.out.println("insertDate: "+chatMapper.selectIDate());
            // == null : insert_date -> 72시간 경과 여부 (259200초)
        } else if(chatMapper.selectUDate() != null) {
            System.out.println("updateDate: "+chatMapper.selectUDate());
        } else {
            System.out.println("챗봇 종료 기능 ERROR");
        }
    }
}