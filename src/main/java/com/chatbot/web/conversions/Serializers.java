package com.chatbot.web.conversions;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.User;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.ExamMapper;
import com.chatbot.web.mappers.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class Serializers {
    @Autowired
    User user;
    @Autowired
    Chat chat;
    @Autowired
    ChatHistory chatHistory;
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    ChatHistoryMapper chatHistoryMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, arrObj;
    private JSONArray arr, arr2;
    private ValueOperations<String, Object> vop;

    public Map<String, Object> fallbackSer(Map<String, Object> jsonParams) {
        mapper = new ObjectMapper();
        parser = new JSONParser();
        obj = new JSONObject();
        obj1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        arr2 = new JSONArray();
        try {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUuserKey = (String) properties.get("botUserKey");
            vop.set(botUuserKey, chat.getId());
            int userKey = (int) vop.get(botUuserKey);

            chat.setChatBody(chatBody);
            if (userKey == 0) {
                System.out.println("auth insert");
                chatMapper.insertData(chat);
            } else if (userKey != 0) {
                System.out.println("auth update");
                chatMapper.updateData(chat);
            } else {
                System.out.println("CHAT DB ERROR");
            }
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            obj2.put("description", "무슨 뜻인지 이해하기 힘들어요.\n챗봇을 이용해주시려면 '로그인' 버튼을,\n종료하시려면 '종료' 버튼을 눌러주세요.");
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg");
            obj2.put("buttons", arr2);
            arr2.add(obj4);
            arr2.add(obj5);
            obj4.put("messageText", "본인인증");
            obj4.put("label", "로그인하기");
            obj4.put("action", "message");
            obj5.put("messageText", "챗봇종료");
            obj5.put("label", "챗봇종료");
            obj5.put("action", "message");
            chatHistory.setChatKind("B");
            chatHistory.setChatBody("폴백메세지");
            chatHistoryMapper.insertData(chatHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Map<String, Object> authSer(Map<String, Object> jsonParams) {
        mapper = new ObjectMapper();
        parser = new JSONParser();
        obj = new JSONObject();
        obj1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        arr2 = new JSONArray();

        try {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUuserKey = (String) properties.get("botUserKey");
            vop.set(botUuserKey, chat.getId());
            int userKey = (int) vop.get(botUuserKey);

            chat.setChatBody(chatBody);
            if(userKey == 0) {
                System.out.println("auth insert");
                chatMapper.insertData(chat);
            } else if(userKey != 0) {
                System.out.println("auth update");
                chatMapper.updateData(chat);
            } else {
                System.out.println("CHAT DB ERROR");
            }
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            if (chatBody.equals("본인인증")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                obj1.put("simpleText", obj2);
                obj2.put("text", "아이디를 입력해주세요.");
                chatHistory.setChatKind("B");
                chatHistory.setChatBody(obj2.get("text").toString());
                chatHistoryMapper.insertData(chatHistory);

                //아이디 체크 로직(else if)
            } else if (chatBody.equals("아이디")) {
                System.out.println("아이디 일치");
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                obj1.put("simpleText", obj2);
                obj2.put("text", "비밀번호를 입력해주세요.");
                chatHistory.setChatKind("B");
                chatHistory.setChatBody(obj2.get("text").toString());
                chatHistoryMapper.insertData(chatHistory);
            } else {
                System.out.println("AUTH ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

//챗봇종료 기능 구현하기
//로그인한 사람만 해당 발화에 반응하게끔 로직을 짜줘야 함
    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
        mapper = new ObjectMapper();
        parser = new JSONParser();
        JSONObject imgObj = new JSONObject();
        obj = new JSONObject();
        obj1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        JSONObject obj5 = new JSONObject();
        JSONObject obj6 = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        arr2 = new JSONArray();
        JSONArray arr3 = new JSONArray();

        try  {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUuserKey = (String) properties.get("botUserKey");
            vop.set(botUuserKey, chat.getId());
            int userKey = (int) vop.get(botUuserKey);

            chat.setChatBody(chatBody);
            System.out.println("chatBody: " + chatBody);
            if(userKey == 0) {
                System.out.println("func insert");
                chatMapper.insertData(chat);
            } else if(userKey != 0) {
                System.out.println("func update");
                chatMapper.updateData(chat);
            } else {
                System.out.println("CHAT DB ERROR");
            }
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);
            
            //1. 시험종류(사용자 이름 가져와야 함..)
            if (chatBody.equals("비밀번호")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                for (int i = 1; i < 5; i++) {
                obj1.put("carousel", obj2);
                obj2.put("type", "basicCard");
                obj2.put("items", arr2);
                arr2.add(obj3);
                obj3.put("title", "[user_name]님, 안녕하세요!\n시험 종류를 선택해주세요.");
                obj3.put("thumbnail", imgObj);
                obj3.put("buttons", arr3);
                imgObj.put("imageUrl", "https://i.pinimg.com/564x/f4/62/c9/f462c9fc876243221c6ee36c3fb97b32.jpg");
                    //오류나는 부분
                    if(i == 1) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "1학기 중간고사");
                        obj4.put("label", "1학기 중간고사");
                        obj4.put("action", "message");
                        obj5.put("messageText", "1학기 기말고사");
                        obj5.put("label", "1학기 기말고사");
                        obj5.put("action", "message");
                    } else if(i ==2) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "1학기 중간고사");
                        obj4.put("label", "2학기 중간고사");
                        obj4.put("action", "message");
                        obj5.put("messageText", "1학기 기말고사");
                        obj5.put("label", "2학기 기말고사");
                        obj5.put("action", "message");
                    } else if(i ==3) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "3월 모의고사");
                        obj4.put("label", "3월 모의고사");
                        obj4.put("action", "message");
                        obj5.put("messageText", "6월 모의고사");
                        obj5.put("label", "6월 모의고사");
                        obj5.put("action", "message");
                    } else if(i ==4) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "9월 모의고사");
                        obj4.put("label", "9월 모의고사");
                        obj4.put("action", "message");
                        obj5.put("messageText", "11월 모의고사");
                        obj5.put("label", "11월 모의고사");
                        obj5.put("action", "message");
                    } else {
                        System.out.println("SUBJECT CODE ERROR");
                    }
                }
                //2. 과목구분
            } else if(chatBody.contains("모의고사")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                for (int i = 1; i < 4; i++) {
                    obj1.put("carousel", obj2);
                    obj2.put("type", "basicCard");
                    obj2.put("items", arr2);
                    arr2.add(obj3);
                    obj3.put("title", "과목 종류를 선택해주세요.");
                    obj3.put("thumbnail", imgObj);
                    obj3.put("buttons", arr3);
                    imgObj.put("imageUrl", "https://i.pinimg.com/564x/f4/62/c9/f462c9fc876243221c6ee36c3fb97b32.jpg");
                    if (i == 1) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "국어");
                        obj4.put("label", "국어");
                        obj4.put("action", "message");
                        obj5.put("messageText", "영어");
                        obj5.put("label", "영어");
                        obj5.put("action", "message");
                    } else if (i == 2) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "1학기 중간고사");
                        obj4.put("label", "2학기 중간고사");
                        obj4.put("action", "message");
                        obj5.put("messageText", "1학기 기말고사");
                        obj5.put("label", "2학기 기말고사");
                        obj5.put("action", "message");
                    } else if (i == 3) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "3월 모의고사");
                        obj4.put("label", "3월 모의고사");
                        obj4.put("action", "message");
                        obj5.put("messageText", "6월 모의고사");
                        obj5.put("label", "6월 모의고사");
                        obj5.put("action", "message");
                    } else if (i == 4) {
                        arr3.add(obj4);
                        arr3.add(obj5);
                        obj4.put("messageText", "수학");
                        obj4.put("label", "수학");
                        obj4.put("action", "message");
                        obj5.put("messageText", "생활과윤리");
                        obj5.put("label", "생활과윤리");
                        obj5.put("action", "message");
                    } else {
                        System.out.println("ERROR");
                    }
                    System.out.println("반복문: " + obj);
                }
                //3. admin_func
            } else if(chatBody.equals("admin analysis")) {

                //4. user_func
            } else if(chatBody.equals("user analysis")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);

                obj1.put("carousel", obj2);
                obj2.put("type", "basicCard");
                obj2.put("items", arr2);

                arr2.add(obj3);

                obj3.put("title", "[시험문제 번호]\n[시험문제 질문]");
                obj3.put("description", "정답: [시험문제 정답]\n선택한 답: [오답]\n[시험문제 내용]");
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", "https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361513_1280.jpg");

                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");

            } else {
                System.out.println("EXAM ERROR");
            }

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
