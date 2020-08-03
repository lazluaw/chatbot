package com.chatbot.web.conversions;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.User;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.ExamMapper;
import com.chatbot.web.mappers.UserMapper;
import com.chatbot.web.proxy.Fallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.Map;

//login : v204ap,l7hi3x0e
@Service
public class Serializer {
    @Autowired User user;
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ExamMapper examMapper;
    @Autowired UserMapper userMapper;
    @Autowired Fallback fallback;
    @Autowired RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, obj6, arrObj;
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

            //test: 인서트, 업데이트 데이트
            System.out.println("insert: "+chatMapper.selectIDate());
            System.out.println("update: "+chatMapper.selectUDate());

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);

            chat.setChatBody(chatBody);
            if (userKey == 0) {
                System.out.println("fallback/auth insert");
                chatMapper.insertData(chat);
            } else if (userKey != 0) {
                System.out.println("fallback/auth update");
                chatMapper.updateData(chat);
            } else {
                System.out.println("fallback ERROR");
            }
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            String[] loginInfo = null;
            if(chatBody.contains(",")) {
                loginInfo = chatBody.split(",");
                System.out.println("id: " + loginInfo[0] + "\npw: " + loginInfo[1]);
                if (loginInfo[0].equals(userMapper.selectId()) && loginInfo[1].equals((userMapper.selectPw())) || chatBody.contains(" ")) {
                    System.out.println("로그인 성공");
                    this.menuSer(jsonParams);
                } else {
                    return fallback.fallbackForm();
                }
            } else {
                return fallback.fallbackForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Map<String, Object> loginSer(Map<String, Object> jsonParams) {
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
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);

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
                obj2.put("text", "아이디와 비밀번호를 입력해주세요.\n(해당 형식으로 작성 : 아이디,비밀번호)");
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("id,pw");
                chatHistoryMapper.insertData(chatHistory);
            } else {
                System.out.println("AUTH ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Map<String, Object> menuSer(Map<String, Object> jsonParams) {
        mapper = new ObjectMapper();
        parser = new JSONParser();
        obj = new JSONObject();
        obj1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        obj6 = new JSONObject();
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
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);

            chat.setChatBody(chatBody);
            if (userKey == 0) {
                System.out.println("menu/exit insert");
                chatMapper.insertData(chat);
            } else if (userKey != 0) {
                System.out.println("menu/exit update");
                chatMapper.updateData(chat);
            } else {
                System.out.println("menu/exit ERROR");
            }
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            String userFunc = "오답노트";
            String adminFunc = "시험분석";

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            obj2.put("description", userMapper.selectName() + "님, 반갑습니다.\n챗봇 메뉴를 선택해주세요 :3");
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg");
            obj2.put("buttons", arr2);
            arr2.add(obj4);
            arr2.add(obj5);
            arr2.add(obj6);
            obj4.put("messageText", "스트리밍");
            obj4.put("label", "스트리밍 들으러가기");
            obj4.put("action", "message");
            obj5.put("messageText", userFunc);
            obj5.put("label", userFunc);
            obj5.put("action", "message");
            obj6.put("messageText", "챗봇종료");
            obj6.put("label", "챗봇종료");
            obj6.put("action", "message");

            chatHistory.setChatKind("B");
            chatHistory.setChatBody(obj2.get("description").toString());
            chatHistoryMapper.insertData(chatHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

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
        obj5 = new JSONObject();
        obj6 = new JSONObject();
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
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);

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
            
            //1. 시험종류(사용자 이름 가져와야 함..) l7hi3x0e
            if (chatBody != null) {
                System.out.println("시험분석 메뉴");
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                for(int i = 0; i < 4; i++) {
                    obj1.put("carousel", obj2);
                    obj2.put("type", "basicCard");
                    obj2.put("items", arr2);
                    arr2.add(obj3);
                    obj3.put("title", "[user_name]님, 안녕하세요!\n시험 종류를 선택해주세요.");
                    obj3.put("thumbnail", imgObj);
                    obj3.put("buttons", arr3);
                    imgObj.put("imageUrl", "https://i.pinimg.com/564x/71/f0/93/71f0937147bc7621ebc0856f95e4bc15.jpg");
                    //오류나는 부분
                    arr3.add(obj4);
                    arr3.add(obj5);
                    obj4.put("messageText", "1학기 중간고사");
                    obj4.put("label", "1학기 중간고사");
                    obj4.put("action", "message");
                    obj5.put("messageText", "1학기 기말고사");
                    obj5.put("label", "1학기 기말고사");
                    obj5.put("action", "message");
                }
                //2. 과목구분
            } else if(chatBody.contains("고사")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                for(int i = 0; i < 4; i++) {
                    obj1.put("carousel", obj2);
                    obj2.put("type", "basicCard");
                    obj2.put("items", arr2);
                    arr2.add(obj3);
                    obj3.put("title", "과목 종류를 선택해주세요.");
                    obj3.put("thumbnail", imgObj);
                    obj3.put("buttons", arr3);
                    imgObj.put("imageUrl", "https://i.pinimg.com/564x/69/bb/22/69bb22e2f8afc4706c62f5d0f73f39c1.jpg");
                    arr3.add(obj4);
                    arr3.add(obj5);
                    arr3.add(obj6);
                    obj4.put("messageText", "국어");
                    obj4.put("label", "국어");
                    obj4.put("action", "message");
                    obj5.put("messageText", "영어");
                    obj5.put("label", "영어");
                    obj5.put("action", "message");
                    obj6.put("messageText", "수학");
                    obj6.put("label", "수학");
                    obj6.put("action", "message");
                }
                //3. admin_func
            } else if(chatBody.equals("국어") || chatBody.equals("영어") || chatBody.equals("수학")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);

                obj1.put("carousel", obj2);
                obj2.put("type", "basicCard");
                obj2.put("items", arr2);

                arr2.add(obj3);

                obj3.put("title", "[학생들이 가장 많이 틀린 문제]: [시험문제 번호]");
                obj3.put("description", "[정답], [학생들이 가장 많이 선택한 오답]\n[시험문제 질문]\n[시험문제 내용]");
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", "https://i.pinimg.com/564x/69/5b/41/695b41f9077d5871b40452679c5b796a.jpg");

                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");

                //4. user_func
            } else if(chatBody.equals("학생")) {
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
                obj4.put("imageUrl", "https://i.pinimg.com/564x/b4/e0/df/b4e0df7b3b0e571a3fa61d4369f3ad31.jpg");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
