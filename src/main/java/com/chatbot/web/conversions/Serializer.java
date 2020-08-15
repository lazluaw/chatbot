package com.chatbot.web.conversions;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.Exam;
import com.chatbot.web.domains.ExamAnalysis;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.ExamAnalysisMapper;
import com.chatbot.web.mappers.ExamMapper;
import com.chatbot.web.proxy.Exit;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

//user : v204ap, l7hi3x0e
//admin: cucumber, cucumber123
@Service
public class Serializer {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatMapper chatMapper;
    @Autowired Exam exam;
    @Autowired ExamAnalysis examAnalysis;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ExamMapper examMapper;
    @Autowired ExamAnalysisMapper examAnalysisMapper;
    @Autowired Fallback fallback;
    @Autowired RedisTemplate<String, Object> redisTemplate;
    @Autowired Exit exit;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr, img, title, description;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, obj6, arrObj;
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
    private String[] subjects = {"국어", "영어", "수학", "경제", "생활과윤리", "지리", "지구과학", "생명과학", "물리"};
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
            vop.set(botUserKey, String.valueOf(chat.getChatId()));

            if (vop.get(botUserKey).equals("0")) {
                vop.set("adminCode", String.valueOf(chatMapper.selectList(392).getUserCode()));
                vop.set("userCode", String.valueOf(chatMapper.selectList(1186).getUserCode()));
                vop.set("adminLogin", chatMapper.selectList(392).getUserId() + ", " + chatMapper.selectList(392).getUserPw());
                vop.set("userLogin", chatMapper.selectList(1186).getUserId() + ", " + chatMapper.selectList(1186).getUserPw());
                if (chatBody.contains("로그인") || chatBody.contains("login")) {
                    return this.loginSer();
                } else if (chatBody.equals(vop.get("adminLogin"))) {
                    logger.info("admin login insert");
                    chat.setChatBody(chatBody);
                    chat.setUserCode(Integer.parseInt(vop.get("adminCode").toString()));
                    chatMapper.insertData(chat);
                    return this.menuSer(jsonParams);
                } else if (chatBody.equals(vop.get("userLogin"))) {
                    logger.info("user login insert");
                    chat.setChatBody(chatBody);
                    chat.setUserCode(Integer.parseInt(vop.get("userCode").toString()));
                    chatMapper.insertData(chat);
                    return this.menuSer(jsonParams);
                } else if (chatBody.contains(",")) {
                    logger.info("로그인실패 fallback");
                    return fallback.failLogin();
                } else {
                    logger.error("비로그인 fallback");
                    return fallback.fallback(jsonParams);
                }
            } else if (vop.get(botUserKey) != "0") {
                if (chatBody.contains("로그인") || chatBody.contains("login")) {
                    logger.info("로그인O 중복");
                    obj.put("version", "2.0");
                    obj.put("template", arrObj);
                    arrObj.put("outputs", arr);
                    arr.add(obj1);
                    obj1.put("basicCard", obj2);
                    obj2.put("title", "이미 로그인하였습니다!");
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

                    chat.setChatBody(chatBody);
                    chat.setUserCode(chat.getUserCode());
                    chatMapper.insertData(chat);

                    chatHistory.setChatId(chat.getChatId());
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
                } else if (chatBody.contains("스트리밍") || chatBody.contains("교육") || chatBody.contains("강의")
                        || chatBody.contains("출석") || chatBody.contains("출결")) {
                    return this.classSer(jsonParams);
                } else if (chatBody.contains("오답노트") || chatBody.contains("시험분석")
                        || chatBody.contains("고사") || Arrays.asList(subjects).contains(chatBody)) {
                    return this.examSer(jsonParams);
                } else if (chatBody.contains("종료")) {
                    return exit.exit(jsonParams);
                } else {
                    logger.info("로그인O fallback");
                    return fallback.fallback(jsonParams);
                }
            } else {
                logger.error("로그인 fallback ERROR");
                return fallback.fallback(jsonParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("로그인 logic ERROR");
            return null;
        }
    }

    public Map<String, Object> loginSer() {
        try {
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("simpleText", obj2);
            obj2.put("text", "피클봇은 로그인 후에 사용하실 수 있습니다.\n아이디와 비밀번호를 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
            return obj;
        } catch (Exception e) {
            logger.error("로그인입력 ERROR");
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
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            //chat: 로그인 후에 바로 메뉴에 왔을 때 데이터가 이중으로 저장되어도 어쩔 수 없..을까?
            chat.setChatBody(chatBody);
            chatMapper.updateData(chat);
            chatHistory.setChatId(chat.getChatId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            String analMenu = "";
            String attMenu = "";
            String url = "";
            String desc = "님, 반갑습니다.\n메뉴를 선택해주세요.\n피클봇을 종료하시려면 '종료'를 입력해주세요.";
            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                attMenu = "출결관리";
                analMenu = "시험분석";
                img = adminMImg;
                url = "https://www.naver.com";
                description = chatMapper.selectList(392).getName() + desc;
                arr2.add(obj6);
                obj6.put("webLinkUrl", "https://www.naver.com");
                obj6.put("label", "화상교육");
                obj6.put("action", "webLink");
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                attMenu = "출석체크 & 화상교육";
                analMenu = "오답노트";
                img = userMImg;
                url = "https://www.naver.com";
                description = chatMapper.selectList(1186).getName() + desc;
            }
            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            obj2.put("description", description);
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", img);
            obj2.put("buttons", arr2);
            arr2.add(obj4);
            arr2.add(obj5);
            obj4.put("webLinkUrl", url);
            obj4.put("label", attMenu);
            obj4.put("action", "webLink");
            obj5.put("messageText", analMenu);
            obj5.put("label", analMenu);
            obj5.put("action", "message");

            chatHistory.setChatKind("B");
            chatHistory.setChatBody("메뉴선택");
            chatHistoryMapper.insertData(chatHistory);
            return obj;
        } catch (Exception e) {
            logger.error("menu logic ERROR");
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
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            chat.setChatBody(chatBody);
            chatMapper.updateData(chat);
            chatHistory.setChatId(chat.getChatId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            if (chatBody.contains("스트리밍") || chatBody.contains("교육") || chatBody.contains("강의")) {
                img = streamImg;
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "화상교육";
                    description = title + "입니다.";
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출석체크&화상교육";
                    description = title + "입니다. 입장 시 자동으로 출석 체크됩니다.";
                }
            } else if (chatBody.contains("출결") || chatBody.contains("출석")) {
                img = attendanceImg;
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출결관리";
                    description = title + "창으로 이동합니다.";
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출석체크 & 화상교육";
                    description = "출석은 화상교육 창에 진입 시 자동으로 체크됩니다.\n화상교육으로 이동하기";
                }
            }
            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            obj2.put("title", title);
            obj2.put("description", description);
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", img);
            obj2.put("buttons", arr2);
            arr2.add(obj4);
            arr2.add(obj6);
            obj4.put("webLinkUrl", "https://www.naver.com");
            obj4.put("label", title);
            obj4.put("action", "webLink");
            obj6.put("messageText", "챗봇종료");
            obj6.put("label", "챗봇종료");
            obj6.put("action", "message");

            chatHistory.setChatKind("B");
            chatHistory.setChatBody(title);
            chatHistoryMapper.insertData(chatHistory);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class logic ERROR");
            return null;
        }
    }

    public Map<String, Object> addJson(Map<String, Object> jsonParams) {
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
            JSONObject obj15 = new JSONObject();
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

            chat.setChatBody(chatBody);
            chatMapper.updateData(chat);
            chatHistory.setChatId(chat.getChatId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            String analyses = "";
            title = "시험종류선택";
            description = "시험종류를 선택해주세요.";
            img = examKindImg;
            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                analyses = "시험분석";
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                analyses = "오답노트";
            } else {
                logger.error("analyses 사용자구분 ERROR");
            }

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("carousel", obj2);
            obj2.put("type", "basicCard");
            obj2.put("items", arr2);
            arr2.add(obj3);
            if (chatBody.equals(analyses)) {
                obj3.put("title", title);
                obj3.put("description", description);
                obj3.put("thumbnail", img);
                obj3.put("buttons", arr3);
                obj4.put("imageUrl", img);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "1학기 중간고사");
                obj5.put("label", "1학기 중간고사");
                obj5.put("action", "message");
                obj6.put("messageText", "1학기 기말고사");
                obj6.put("label", "1학기 기말고사");
                obj6.put("action", "message");

                arr2.add(obj7);
                obj7.put("title", "시험종류선택");
                obj7.put("description", "챗봇을 종료하시려면 '종료'를 입력해주세요.");
                obj7.put("thumbnail", obj4);
                obj7.put("buttons", arr4);
                obj4.put("imageUrl", img);
                arr4.add(obj8);
                arr4.add(obj9);
                obj8.put("messageText", "2학기 중간고사");
                obj8.put("label", "2학기 중간고사");
                obj8.put("action", "message");
                obj9.put("messageText", "2학기 기말고사");
                obj9.put("label", "2학기 기말고사");
                obj9.put("action", "message");

                chatHistory.setChatKind("B");
                chatHistory.setChatBody(title);
                chatHistoryMapper.insertData(chatHistory);
                return obj;

            } else if (chatBody.equals("1학기 중간고사") || chatBody.equals("1학기 기말고사")
                    || chatBody.equals("2학기 중간고사") || chatBody.equals("2학기 기말고사")
                    || chatBody.contains("1학기 중간고사") && chatBody.contains(analyses)
                    || chatBody.contains("2학기 중간고사") && chatBody.contains(analyses)
                    || chatBody.contains("1학기 기말고사") && chatBody.contains(analyses)
                    || chatBody.contains("2학기 기말고사") && chatBody.contains(analyses)) {
                title = "과목종류선택";
                description = "과목종류를 선택해주세요.";
                img = subjectCodeImg;
                obj3.put("title", title);
                obj3.put("description", description);
                obj3.put("thumbnail", obj4);
                obj3.put("buttons", arr3);
                obj4.put("imageUrl", img);
                arr3.add(obj5);
                arr3.add(obj6);
                arr3.add(obj7);
                obj5.put("messageText", subjects[0]);
                obj5.put("label", subjects[0]);
                obj5.put("action", "message");
                obj6.put("messageText", subjects[1]);
                obj6.put("label", subjects[1]);
                obj6.put("action", "message");
                obj7.put("messageText", subjects[2]);
                obj7.put("label", subjects[2]);
                obj7.put("action", "message");

                arr2.add(obj8);
                obj8.put("title", title);
                obj8.put("description", description);
                obj8.put("thumbnail", obj4);
                obj8.put("buttons", arr4);
                obj4.put("imageUrl", img);
                arr4.add(obj9);
                arr4.add(obj10);
                arr4.add(obj11);
                obj9.put("messageText", subjects[3]);
                obj9.put("label", subjects[3]);
                obj9.put("action", "message");
                obj10.put("messageText", subjects[4]);
                obj10.put("label", subjects[4]);
                obj10.put("action", "message");
                obj11.put("messageText", subjects[5]);
                obj11.put("label", subjects[5]);
                obj11.put("action", "message");

                arr2.add(obj12);
                obj12.put("title", title);
                obj12.put("thumbnail", obj4);
                obj12.put("buttons", arr5);
                obj4.put("imageUrl", subjectCodeImg);
                arr5.add(obj13);
                arr5.add(obj14);
                arr5.add(obj15);
                obj13.put("messageText", subjects[6]);
                obj13.put("label", subjects[6]);
                obj13.put("action", "message");
                obj14.put("messageText", subjects[7]);
                obj14.put("label", subjects[7]);
                obj14.put("action", "message");
                obj15.put("messageText", subjects[8]);
                obj15.put("label", subjects[8]);
                obj15.put("action", "message");

                chatHistory.setChatKind("B");
                chatHistory.setChatBody(title);
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else if (Arrays.asList(subjects).contains(chatBody)
                    || (chatBody.contains("1학기 중간고사") && Arrays.asList(subjects).contains(chatBody)) && chatBody.contains(analyses)
                    || (chatBody.contains("1학기 중간고사") && Arrays.asList(subjects).contains(chatBody)) && chatBody.contains(analyses)
                    || (chatBody.contains("1학기 기말고사") && Arrays.asList(subjects).contains(chatBody)) && chatBody.contains(analyses)
                    || (chatBody.contains("2학기 기말고사") && Arrays.asList(subjects).contains(chatBody)) && chatBody.contains(analyses)) {
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    //test: 버튼으로 왔을 때 vs 발화로 왔을 때를 구분해주어야 함(if)
                    //틀린 개수가 몇 개인지 세어봐야 하고 두 개 이상일 때에는 반복문 로직이 추가되어야 함
                    title = "1학기 중간고사 국어 14번";
                    description = "[정답: 4번]\n-학생들이 가장 많이 선택한 오답: 2번\n어휘의 형성 체계가 가장 다른 것은?\n1. 손쉽다   2. 맛나다\n3. 시름없다   4. 남다르다";
                    img = adminAnImg;
                    Exam examList = examMapper.selectList();
                    ExamAnalysis wEList = examAnalysisMapper.selectList(Integer.parseInt(vop.get("adminCode").toString()));
                    obj.put("version", "2.0");
                    obj.put("template", arrObj);
                    arrObj.put("outputs", arr);
                    arr.add(obj1);
                    obj1.put("basicCard", obj3);
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    Exam examList = examMapper.selectList();
                    ExamAnalysis wEList = examAnalysisMapper.selectList(Integer.parseInt(vop.get("userCode").toString()));
                    title = "1학기 기말고사 국어\n" + examList.getExamNum() + "번";
                    description = "[정답: 2, 4번]\n-내가 선택한 오답: 3번\n잘못 짝지어진 것은?\n1. 들: ㄷㄹ-   2. 뜻: ㄷㅇㅅ-\n3. 생각: ㅅ-   4. 뿐: ㅈㅂㄹ-";
                    img = userAnImg;
                }
                obj3.put("title", title);
                obj3.put("description", description);
                obj3.put("thumbnail", img);
                obj3.put("buttons", arr3);
                obj4.put("imageUrl", img);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", "처음으로");
                obj5.put("label", "처음으로");
                obj5.put("action", "message");
                obj6.put("messageText", "챗봇종료");
                obj6.put("label", "챗봇종료");
                obj6.put("action", "message");

                chatHistory.setChatKind("B");
                chatHistory.setChatBody(analyses);
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else {
                logger.error("analyses logic ERROR");
                return null;
            }
        } catch (Exception e) {
            logger.error("analysis logic ERROR");
            e.printStackTrace();
            return null;
        }
    }
}