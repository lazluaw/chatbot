/*
**학생 출석 기능이 따로 있는 경우
obj4.put("messageText", attendance);
            obj4.put("label", attendance);
            obj4.put("action", "message");
            obj5.put("messageText", analysis);
            obj5.put("label", analysis);
            obj5.put("action", "message");
 */

/*
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Serializerss {
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
    private String entryImg = "https://i.pinimg.com/564x/f4/62/c9/f462c9fc876243221c6ee36c3fb97b32.jpg";
    private String streamImg = "https://i.pinimg.com/564x/df/9b/65/df9b65458c037b6f9eac74e9035c62ad.jpg";
    private String examKindImg = "https://i.pinimg.com/564x/81/21/12/8121120d992f697528c3ca99d9af4baa.jpg";
    private String subjectCodeImg = "https://i.pinimg.com/564x/9a/48/f4/9a48f4460b011bf688e50c03165dadd6.jpg";
    private String attendanceImg = "https://i.pinimg.com/564x/82/e5/db/82e5dbfb38770d47232532c5c5e3c932.jpg";
    private String adminMImg = "https://i.pinimg.com/564x/c3/d1/42/c3d1428151d94f028e3c9d1e5d86ea8f.jpg";
    private String adminAnImg = "https://i.pinimg.com/564x/e7/c3/cc/e7c3cc68c94b5828e347c0e35255425d.jpg";
    private String userMImg = "https://i.pinimg.com/564x/cc/8e/08/cc8e083f2c13532a94038138a6713ec1.jpg";
    private String userAnImg = "https://i.pinimg.com/564x/7e/0a/70/7e0a70b7dc579def017a4cd56ad826f9.jpg";
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

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);
            chat.setChatBody(chatBody);

            if (userKey == 0) {
                logger.info("fallback/auth insert");
                chatMapper.insertData(chat);
                chatHistory.setChatId(chat.getId());
            } else if (userKey != 0) {
                    logger.info("fallback/auth update");
                    chatMapper.updateData(chat);
            } else {
                logger.error("fallback update ERROR");
            }

            if (chatHistoryMapper.selectLogin(chat.getId()) == null) {
                if (chatBody.contains("로그인") || chatBody.contains("login")) {
                    return this.loginSer(jsonParams);
                } else if (chatBody.contains(",")) {
                    if (chatBody.contains(" ") || chatBody.equals(userMapper.selectLoginList(100000002)) || chatBody.contains(" ")) {
                        chat.setUserCode(100000002);
                        chatMapper.updateData(chat);
                        chatHistory.setChatId(chat.getId());
                        chatHistory.setUserInfo(userInfo);
                        chatHistory.setChatKind("S");
                        chatHistory.setChatBody("로그인성공");
                        chatHistoryMapper.insertData(chatHistory);
                        return this.menuSer(jsonParams);
                    } else if (chatBody.contains(" ") || chatBody.equals(userMapper.selectLoginList(100020001)) || chatBody.contains(" ")) {
                        chat.setUserCode(100020001);
                        chatMapper.updateData(chat);
                        chatHistory.setChatId(chat.getId());
                        chatHistory.setUserInfo(userInfo);
                        chatHistory.setChatKind("S");
                        chatHistory.setChatBody("로그인성공");
                        chatHistoryMapper.insertData(chatHistory);
                        return this.menuSer(jsonParams);
                    } else {
                        logger.error("사용자 판별 오류");
                        return fallback.fallbackForm(jsonParams);
                    }
                } else {
                    logger.error("로그인 안 된 상태서 fallback 처리");
                    return fallback.fallbackForm(jsonParams);
                }
            } else if (chatHistoryMapper.selectLogin(chat.getId()) != null) {
                logger.info("S일때, if 전에 진입...");
                if (chatBody.contains("로그인") || chatBody.contains("login")) {
                    logger.info("로그인중복블록 진입");
                    obj.put("version", "2.0");
                    obj.put("template", arrObj);
                    arrObj.put("outputs", arr);
                    arr.add(obj1);
                    obj1.put("basicCard", obj2);
                    obj2.put("description", "이미 로그인이 되어 있는 상태입니다 :)\n어디로 이동할까요?");
                    obj2.put("thumbnail", obj3);
                    obj3.put("imageUrl", entryImg);
                    obj2.put("buttons", arr2);
                    arr2.add(obj4);
                    arr2.add(obj5);
                    obj4.put("messageText", "메뉴");
                    obj4.put("label", "메뉴");
                    obj4.put("action", "message");
                    obj5.put("messageText", "챗봇종료");
                    obj5.put("label", "챗봇종료");
                    obj5.put("action", "message");

                    chatHistory.setChatId(chat.getId());
                    chatHistory.setChatKind("C");
                    chatHistory.setUserInfo(userInfo);
                    chatHistory.setChatBody(chatBody);
                    chatHistoryMapper.insertData(chatHistory);

                    chatHistory.setChatKind("B");
                    chatHistory.setChatBody("로그인중복");
                    chatHistoryMapper.insertData(chatHistory);
                    return obj;
                } else if (chatBody.contains("메뉴") || chatBody.contains("menu")) {
                    return this.menuSer(jsonParams);
                } else if (chatBody.contains("스트리밍") || chatBody.contains("교육") || chatBody.contains("강의") || chatBody.contains("출석") || chatBody.contains("출결") || chatBody.contains("추럭")) {
                    return this.classSer(jsonParams);
                } else if (chatBody.contains("오답노트") || chatBody.contains("시험분석")) {
                    return this.examSer(jsonParams);
                } else {
                    return fallback.fallbackForm(jsonParams);
                }
            } else {
                logger.error("나머지는 다 폴백");
                return fallback.fallbackForm(jsonParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("fallback ERROR CATCH");
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
            if (userKey == 0) {
                logger.info("auth insert");
                chatMapper.insertData(chat);
                chatHistory.setChatId(chat.getId());
            } else if (userKey != 0) {
                logger.info("fallback/auth update");
                chatMapper.updateData(chat);
            } else {
                logger.error("fallback update ERROR");
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
            obj2.put("text", "피클봇은 로그인 후에 사용하실 수 있습니다.\n아이디와 비밀번호를 입력해 주세요.\n(형식 : 아이디,비밀번호)");
            chatHistory.setChatKind("B");
            chatHistory.setChatBody("로그인입력");
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
                logger.info("menu/exit insert");
                chatMapper.insertData(chat);
                chatHistory.setChatId(chat.getId());
            } else if (userKey != 0) {
                logger.info("fallback/auth update");
                chatMapper.updateData(chat);
            } else {
                logger.error("menu/exit ERROR");
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

            //user : v204ap, l7hi3x0e
            //admin: cucumber, cucumber123
            String analysis = "";
            String attendance = "";
            String img = "";
            String desc = "님, 반갑습니다.\n챗봇 메뉴를 선택해주세요 :3\n챗봇을 종료하시려면 '종료'를 입력해주세요.";
            String description = userMapper.selectNameList(chat.getUserCode()).getName().concat(desc);
            if (chatMapper.selectUserCode(chat.getId()).getUserCode() == 100000002) {
                attendance = "출결관리";
                analysis = "시험분석";
                img = adminMImg;
            } else if (chatMapper.selectUserCode(chat.getId()).getUserCode() == 100020001) {
                attendance = "출석체크";
                analysis = "오답노트";
                img = userMImg;
            }
            obj2.put("description", description);
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
            obj6.put("webLinkUrl", "https://www.naver.com");
            obj6.put("label", "스트리밍");
            obj6.put("action", "webLink");

            chatHistory.setChatKind("B");
            chatHistory.setChatBody("메뉴선택");
            chatHistoryMapper.insertData(chatHistory);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> classSer(Map<String, Object> jsonParams) {
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
                logger.info("class insert");
                chatMapper.insertData(chat);
                chatHistory.setChatId(chat.getId());
            } else if (userKey != 0) {
                logger.info("fallback/auth update");
                chatMapper.updateData(chat);
            } else {
                    logger.error("update ERROR");
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

            if (chatBody.contains("스트리밍") || chatBody.contains("교육") || chatBody.contains("강의")) {
                obj2.put("title", "스트리밍 메뉴");
                obj2.put("description", "화상교육 시작해볼까요~?");
                obj2.put("thumbnail", obj3);
                obj3.put("imageUrl", streamImg);
                obj2.put("buttons", arr2);
                arr2.add(obj4);
                arr2.add(obj5);
                obj4.put("webLinkUrl", "https://www.naver.com");
                obj4.put("label", "스트리밍");
                obj4.put("action", "webLink");
                obj5.put("messageText", "챗봇종료");
                obj5.put("label", "챗봇종료");
                obj5.put("action", "message");

                chatHistory.setChatKind("B");
                chatHistory.setChatBody(obj2.get("title").toString());
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else if (chatBody.contains("출석") || chatBody.contains("출결") || chatBody.contains("추럭")) {
                String attendance = "";
                String title = attendance + " 메뉴";
                String atUrl = "https://www.daum.net";
                if (chatMapper.selectUserCode(chat.getId()).getUserCode() == 100000002) {
                    attendance = "출결관리";
                } else if (chatMapper.selectUserCode(chat.getId()).getUserCode() == 100020001) {
                    attendance = "출석";
                }
                obj2.put("title", title);
                obj2.put("description", attendance + "하러 출발해볼까요~?");
                obj2.put("thumbnail", obj3);
                obj3.put("imageUrl", attendanceImg);
                obj2.put("buttons", arr2);
                arr2.add(obj4);
                arr2.add(obj5);
                obj4.put("webLinkUrl", atUrl);
                obj4.put("label", attendance);
                obj4.put("action", "webLink");
                obj5.put("messageText", "챗봇종료");
                obj5.put("label", "챗봇종료");
                obj5.put("action", "message");

                chatHistory.setChatKind("B");
                chatHistory.setChatBody(title);
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else {
                return this.menuSer(jsonParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("stream menu ERROR");
            return null;
        }
    }

    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
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

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);
            chat.setChatBody(chatBody);

            String analyses = "";

            if (userKey == 0) {
                logger.info("exam insert");
                chatHistory.setChatId(chat.getId());
                chatMapper.insertData(chat);
            } else if (userKey != 0) {
                logger.info("fallback/auth update");
                chatMapper.updateData(chat);
            } else {
                logger.error("update ERROR");
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

            if (chatBody.contains(analyses)) {
                obj3.put("title", "시험종류 선택");
                obj3.put("description", "시험종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj3.put("thumbnail", imgObj);
                obj3.put("buttons", arr3);
                imgObj.put("imageUrl", examKindImg);
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
                imgObj.put("imageUrl", examKindImg);
                arr4.add(obj7);
                arr4.add(obj8);
                obj7.put("messageText", "2학기 중간고사");
                obj7.put("label", "2학기 중간고사");
                obj7.put("action", "message");
                obj8.put("messageText", "2학기 기말고사");
                obj8.put("label", "2학기 기말고사");
                obj8.put("action", "message");

                chatHistory.setChatId(chat.getId());
                chatHistory.setChatKind("B");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(obj3.get("title").toString());
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else if (chatBody.equals("1학기 중간고사") || chatBody.equals("2학기 중간고사")
                    || chatBody.equals("2학기 중간고사") || chatBody.equals("2학기 기말고사")
                    || chatBody.contains("1학기 중간고사") && chatBody.contains(analyses)
                    || chatBody.contains("2학기 중간고사") && chatBody.contains(analyses)
                    || chatBody.contains("1학기 기말고사") && chatBody.contains(analyses)
                    || chatBody.contains("2학기 기말고사") && chatBody.contains(analyses)) {
                obj3.put("title", "과목종류 선택");
                obj3.put("description", "과목종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj3.put("thumbnail", imgObj);
                obj3.put("buttons", arr3);
                imgObj.put("imageUrl", subjectCodeImg);
                arr3.add(obj4);
                arr3.add(obj5);
                arr3.add(obj6);
                obj4.put("messageText", "국어");
                obj4.put("label", chatBody+" 국어");
                obj4.put("action", "message");
                obj5.put("messageText", "영어");
                obj5.put("label", chatBody+" 영어");
                obj5.put("action", "message");
                obj6.put("messageText", "수학");
                obj6.put("label", chatBody+" 수학");
                obj6.put("action", "message");

                arr2.add(obj7);
                obj7.put("title", "과목종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj7.put("thumbnail", imgObj);
                obj7.put("buttons", arr4);
                imgObj.put("imageUrl", subjectCodeImg);
                arr4.add(obj8);
                arr4.add(obj9);
                arr4.add(obj10);
                obj8.put("messageText", "생활과윤리");
                obj8.put("label", chatBody+" 생활과윤리");
                obj8.put("action", "message");
                obj9.put("messageText", "지리");
                obj9.put("label", chatBody+" 지리");
                obj9.put("action", "message");
                obj10.put("messageText", "경제");
                obj10.put("label", chatBody+" 경제");
                obj10.put("action", "message");

                arr2.add(obj11);
                obj11.put("title", "과목종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj11.put("thumbnail", imgObj);
                obj11.put("buttons", arr5);
                imgObj.put("imageUrl", subjectCodeImg);
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

                chatHistory.setChatId(chat.getId());
                chatHistory.setChatKind("B");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(obj3.get("title").toString());
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else if ((chatBody.contains("1학기 중간고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("2학기 중간고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("1학기 기말고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("2학기 기말고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("1학기 중간고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("2학기 중간고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("1학기 기말고사") && chatBody.contains("국어")) || chatBody.contains(analyses)
                    || (chatBody.contains("2학기 기말고사") && chatBody.contains("국어")) || chatBody.contains(analyses)) {
                obj3.put("title", "[학생들이 가장 많이 틀린 문제]: [시험문제 번호]");
                obj3.put("description", "[정답], [학생들이 가장 많이 선택한 오답]\n[시험문제 질문]\n[시험문제 내용]");
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", adminAnImg);
                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");

                chatHistory.setChatId(chat.getId());
                chatHistory.setChatKind("B");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody("시험분석");
                chatHistoryMapper.insertData(chatHistory);
                return obj;
                //추가하기
            } else if (chatBody.contains("1학기 중간고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("2학기 중간고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("1학기 기말고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("2학기 기말고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("1학기 중간고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("2학기 중간고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("1학기 기말고사") && chatBody.contains(analyses) && chatBody.contains("국어")
                    || chatBody.contains("2학기 기말고사") && chatBody.contains(analyses) && chatBody.contains("국어")) {

                obj3.put("title", "[시험문제 번호]\n[시험문제 질문]");
                obj3.put("description", "정답: [시험문제 정답]\n선택한 답: [오답]\n[시험문제 내용]");
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", userAnImg);
                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");

                chatHistory.setChatId(chat.getId());
                chatHistory.setChatKind("B");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody("오답노트");
                chatHistoryMapper.insertData(chatHistory);
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
 */