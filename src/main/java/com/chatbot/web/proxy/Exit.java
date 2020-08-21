package com.chatbot.web.proxy;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.domains.Chat;
import com.chatbot.web.mappers.ChatMapper;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Map;

@Component @Lazy
@MappedTypes(LocalDate.class)
public class Exit {
    @Autowired Chat chat;
    @Autowired ChatMapper chatMapper;
    @Autowired Serializer serializer;
    @Autowired RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ValueOperations<String, Object> vop;
    public Map<String, Object> exit(Map<String, Object> jsonParams) {
        try {
            vop = redisTemplate.opsForValue();
            vop.set("text", "피클봇이 종료됩니다. 감사합니다.");
            if (chat.getChatId() == 0) {
                return serializer.addJson("sim", "0");
            } else if (chat.getChatId() != 0) {
                serializer.addData(jsonParams, "b", null);
                serializer.addData(jsonParams, "c", null);
                serializer.addData(jsonParams, "b", "사용자종료");
                chat.setChatId(0);
                //test: null이면 안 나오는 이유를 알 수 없음
                return serializer.addJson("sim", "0");
                //test: 현재 시스템 종료가 되고 있지 않은 상황
            } else {
                if (chatMapper.selectList().getChatBody() != "종료") {
                    if (chatMapper.selectList().getUpdateDate() == null) {
                        if (Integer.parseInt(chatMapper.selectList().getInsertDateDiffCnt().toString()) == -3) {
                            serializer.addData(jsonParams, "b", "시스템종료");
                            chat.setChatId(0);
                            return null;
                        } else {
                            logger.error("시스템종료 insert ERROR");
                            return null;
                        }
                    } else if (chatMapper.selectList().getUpdateDate() != null) {
                        if (Integer.parseInt(chatMapper.selectList().getUpdateDateDiffCnt().toString()) == -3) {
                            serializer.addData(jsonParams, "b", "사용자종료");
                            chat.setChatId(0);
                            return null;
                        } else {
                            logger.error("시스템종료 update ERROR");
                            return null;
                        }
                    } else {
                        logger.error("시스템종료 3일 측정 ERROR");
                        return null;
                    }
                } else {
                    logger.error("시스템종료 ERROR");
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exit ERROR");
            return null;
        }
    }
}