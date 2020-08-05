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
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.Map;

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
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, obj6, arrObj, imgObj;
    private JSONArray arr, arr2;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public Map<String, Object> fallbackSer(Map<String, Object> jsonParams) {
        try {
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
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            //test: 인서트, 업데이트 데이트
            System.out.println("insert: " + chatMapper.selectIDate());
            System.out.println("update: " + chatMapper.selectUDate());

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

            //chathistory에서 이전 대화를 가져와서 로그인을 했는지, 로그인에 성곡했는지 비교를 해야 하 것 같음

/*
            chatHistory.setChatKind("S");
            chatHistory.setChatBody("로그인성공");
            chatHistoryMapper.insertData(chatHistory);
            if(chatHistoryMapper.selectLogin() == null) {}
*/



                String[] loginInfo = null;
            if (chatBody.contains(",")) {
                loginInfo = chatBody.split(",");
                System.out.println("id: " + loginInfo[0] + "\npw: " + loginInfo[1]);
                if (loginInfo[0].equals(userMapper.selectUserId()) && loginInfo[1].equals((userMapper.selectUserPw()))
                        || chatBody.contains(" ")
                        || loginInfo[0].equals(userMapper.selectAdminId()) && loginInfo[1].equals(userMapper.selectAdminPw())) {
                    System.out.println("로그인 성공");
                    //차후에 로그인 확인 용도로 쓰기 위함
                    chatHistory.setChatKind("S");
                    chatHistory.setChatBody("로그인성공");
                    chatHistoryMapper.insertData(chatHistory);


                    System.out.println("로그인 성공 여부 가져오기" + chatHistoryMapper.selectLogin());
                    this.menuSer(jsonParams);
                    return obj;
                } else {
                    logger.error("로그인 로직 오류");
                    return fallback.fallbackForm();
                }
            }
            else if(chatBody.contains("메뉴") || chatBody.contains("menu")) {
                System.out.println("사용자 판별 필요");
                //
                return null;
//            } else if() {

            } else {
                logger.error("블록 오류");
                return fallback.fallbackForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> loginSer(Map<String, Object> jsonParams) {
        try {
            mapper = new ObjectMapper();
            parser = new JSONParser();
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
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

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("simpleText", obj2);
            obj2.put("text", "아이디와 비밀번호를 입력해주세요.\n(형식(구분은 , 쉼표입니다) : 아이디,비밀번호)");
            chatHistory.setChatKind("B");
            chatHistory.setChatBody("id,pw");
            chatHistoryMapper.insertData(chatHistory);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> menuSer(Map<String, Object> jsonParams) {
        try {
            mapper = new ObjectMapper();
            parser = new JSONParser();
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            obj3 = new JSONObject();
            obj4 = new JSONObject();
            obj5 = new JSONObject();
            obj6 = new JSONObject();
            imgObj = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
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

            String[] loginInfo = null;
            if(chatBody.contains(",")) {
                loginInfo = chatBody.split(",");
            } else {
                System.out.println("id, pw 판별 오류");
            }
            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);

            //user : v204ap, l7hi3x0e
            //admin: cucumber, cucumber123

            String analysis = "";
            String attendance = "";
            String img = "";
            String stram = "스트리밍";
            if(loginInfo[0].equals(userMapper.selectAdminId())) {
                attendance = "출결관리";
                analysis = "시험분석";
                img = "https://i.pinimg.com/564x/c3/d1/42/c3d1428151d94f028e3c9d1e5d86ea8f.jpg";
                obj2.put("description", userMapper.selectAdminName() + "님, 반갑습니다.\n챗봇 메뉴를 선택해주세요 :3\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
            } else if(loginInfo[0].equals(userMapper.selectUserId())) {
                attendance = "출석체크";
                analysis = "오답노트";
                img = "https://i.pinimg.com/564x/cc/8e/08/cc8e083f2c13532a94038138a6713ec1.jpg";
                obj2.put("description", userMapper.selectUserName() + "님, 반갑습니다.\n챗봇 메뉴를 선택해주세요 :3\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
            }
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", img);
            obj2.put("buttons", arr2);
            arr2.add(obj4);
            arr2.add(obj5);
            arr2.add(obj6);
            obj4.put("messageText", attendance);
            obj4.put("label", attendance);
            obj4.put("action", "message");
            obj5.put("messageText", analysis);
            obj5.put("label", analysis);
            obj5.put("action", "message");
            obj6.put("messageText", stram);
            obj6.put("label", stram+"하러가기");
            obj6.put("action", "message");

            chatHistory.setChatKind("B");
            chatHistory.setChatBody(obj2.get("description").toString());
            chatHistoryMapper.insertData(chatHistory);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
        try  {
            mapper = new ObjectMapper();
            parser = new JSONParser();
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            obj3 = new JSONObject();
            obj4 = new JSONObject();
            obj5 = new JSONObject();
            obj6 = new JSONObject();
            JSONObject obj7 = new JSONObject();
            JSONObject obj8 = new JSONObject();
            JSONObject obj9 = new JSONObject();
            JSONObject obj10 = new JSONObject();
            JSONObject obj11 = new JSONObject();
            JSONObject obj12 = new JSONObject();
            JSONObject obj13 = new JSONObject();
            JSONObject obj14 = new JSONObject();
            imgObj = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
            JSONArray arr3 = new JSONArray();
            JSONArray arr4 = new JSONArray();
            JSONArray arr5 = new JSONArray();
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            JSONObject intent = (JSONObject) jsonPar.get("intent");
            String block = (String) intent.get("name");

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

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("carousel", obj2);
            obj2.put("type", "basicCard");
            obj2.put("items", arr2);
            arr2.add(obj3);
            if(block.contains("100")) {
                obj3.put("title", "시험 종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj3.put("thumbnail", imgObj);
                obj3.put("buttons", arr3);
                imgObj.put("imageUrl", "https://i.pinimg.com/564x/71/f0/93/71f0937147bc7621ebc0856f95e4bc15.jpg");
                arr3.add(obj4);
                arr3.add(obj5);
                obj4.put("messageText", "1학기 중간고사");
                obj4.put("label", "1학기 중간고사");
                obj4.put("action", "message");
                obj5.put("messageText", "1학기 기말고사");
                obj5.put("label", "1학기 기말고사");
                obj5.put("action", "message");

                arr2.add(obj6);
                obj6.put("title", "시험 종류를 선택해주세요.");
                obj6.put("thumbnail", imgObj);
                obj6.put("buttons", arr4);
                imgObj.put("imageUrl", "https://i.pinimg.com/564x/71/f0/93/71f0937147bc7621ebc0856f95e4bc15.jpg");
                arr4.add(obj7);
                arr4.add(obj8);
                obj7.put("messageText", "2학기 중간고사");
                obj7.put("label", "2학기 중간고사");
                obj7.put("action", "message");
                obj8.put("messageText", "2학기 기말고사");
                obj8.put("label", "2학기 기말고사");
                obj8.put("action", "message");
                return obj;
            } else if(block.contains("101")) {
                obj3.put("title", "과목 종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
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

                arr2.add(obj7);
                obj7.put("title", "과목 종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj7.put("thumbnail", imgObj);
                obj7.put("buttons", arr4);
                imgObj.put("imageUrl", "https://i.pinimg.com/564x/69/bb/22/69bb22e2f8afc4706c62f5d0f73f39c1.jpg");
                arr4.add(obj8);
                arr4.add(obj9);
                arr4.add(obj10);
                obj8.put("messageText", "생활과 윤리");
                obj8.put("label", "생활과 윤리");
                obj8.put("action", "message");
                obj9.put("messageText", "지리");
                obj9.put("label", "지리");
                obj9.put("action", "message");
                obj10.put("messageText", "경제");
                obj10.put("label", "경제");
                obj10.put("action", "message");

                arr2.add(obj11);
                obj11.put("title", "과목 종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj11.put("thumbnail", imgObj);
                obj11.put("buttons", arr5);
                imgObj.put("imageUrl", "https://i.pinimg.com/564x/69/bb/22/69bb22e2f8afc4706c62f5d0f73f39c1.jpg");
                arr5.add(obj12);
                arr5.add(obj13);
                arr5.add(obj14);
                obj12.put("messageText", "물리");
                obj12.put("label", "물리");
                obj12.put("action", "message");
                obj13.put("messageText", "생명과학");
                obj13.put("label", "생명과학");
                obj13.put("action", "message");
                obj14.put("messageText", "지구과학");
                obj14.put("label", "지구과학");
                obj14.put("action", "message");
                return obj;
            } else if(block.contains("102")) {
                obj3.put("title", "[학생들이 가장 많이 틀린 문제]: [시험문제 번호]");
                obj3.put("description", "[정답], [학생들이 가장 많이 선택한 오답]\n[시험문제 질문]\n[시험문제 내용]");
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", "https://i.pinimg.com/564x/ae/68/4c/ae684c1987f27e7e365727d18811202d.jpg");
                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");
                return obj;
            } else if(block.contains("112")) {
                obj3.put("title", "[시험문제 번호]\n[시험문제 질문]");
                obj3.put("description", "정답: [시험문제 정답]\n선택한 답: [오답]\n[시험문제 내용]");
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", "https://i.pinimg.com/564x/7e/0a/70/7e0a70b7dc579def017a4cd56ad826f9.jpg");
                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");
                return obj;
            } else {
                logger.error("시험종류 - 과목코드 포맷 오류");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}