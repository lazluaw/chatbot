package com.chatbot.web.proxy;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.domains.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Fallback {
    @Autowired Serializer serializer;
    @Autowired Chat chat;
    @Autowired RedisTemplate redisTemplate;
    private ValueOperations<String, Object> vop;
    private String text;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public Map<String, Object> fallback(Map<String, Object> jsonParams) {
        try {
            vop = redisTemplate.opsForValue();
            vop.set("firstMes", "화상교육");
            logger.info("fallback 진입");
            if (chat.getChatId() != 0) {
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    vop.set("secondMes", "시험분석");
                    vop.set("thirdMes", "출결관리");
//                    chat.setUserCode(Integer.valueOf(vop.get("adminCode").toString()));
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    vop.set("secondMes", "오답노트");
                    vop.set("thirdMes", "출석체크");
//                    chat.setUserCode(Integer.valueOf(vop.get("userCode").toString()));
                }
                if (vop.get("overlap").equals("1")) {
                    logger.info("로그인O 중복");
                    text = "이미 로그인이 되어 있는 상태입니다.";
                    vop.set("overlap", "0");
                } else {
                    logger.info("로그인O fallback");
                    text = "무슨 의미인지 모르겠어요.";
                }
                vop.set("text", text + (" 원하는 단어를 선택해주세요."));
                serializer.addData(jsonParams, "b", null);
                serializer.addData(jsonParams, "c", null);
                serializer.addData(jsonParams, "b", "로그인폴백");
                return serializer.addJson("sim", "qBut");
            } else {
                logger.info("로그인전 fallback");
                return serializer.loginSer();
            }
        } catch (Exception e) {
            logger.error("fallback ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> failLogin() {
        try {
            vop = redisTemplate.opsForValue();
            vop.set("lastMes", "");
            vop.set("text", "로그인에 실패하였습니다.\n다시 한 번 정확히 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
            return serializer.addJson("sim", "0");
        } catch (Exception e) {
            logger.info("로그인 실패 logic ERROR");
            e.printStackTrace();
            return null;
        }
    }
}
