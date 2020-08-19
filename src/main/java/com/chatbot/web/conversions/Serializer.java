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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
    private static ObjectMapper mapper = new ObjectMapper();
    private JSONParser parser = new JSONParser();
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, arrObj;
    private JSONArray arr, arr2, arr3, arr4;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String jsonStr, img, title, description;
    private String subjectCodeImg = "https://i.pinimg.com/564x/9a/48/f4/9a48f4460b011bf688e50c03165dadd6.jpg";
    private String adminAnImg = "https://i.pinimg.com/564x/e7/c3/cc/e7c3cc68c94b5828e347c0e35255425d.jpg";
    private String userAnImg = "https://i.pinimg.com/564x/7e/0a/70/7e0a70b7dc579def017a4cd56ad826f9.jpg";

    public Map<String, Object> addJson(String format, String button) {
        try {
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            obj3 = new JSONObject();
            obj4 = new JSONObject();
            obj5 = new JSONObject();
            obj6 = new JSONObject();
            obj7 = new JSONObject();
            obj8 = new JSONObject();
            obj9 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
            arr3 = new JSONArray();
            vop = redisTemplate.opsForValue();
            vop.set("exitMessage", "챗봇종료");
            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            if (format.equals("sim")) {
                obj1.put("simpleText", obj2);
                obj2.put("text", vop.get("text"));
                return obj;
            } else if (format.equals("bas")) {
                System.out.println("bas 진입");
                obj1.put("basicCard", obj2);
                obj2.put("title", vop.get("title"));
                obj2.put("description", vop.get("description"));
                obj2.put("thumbnail", obj3);
                obj3.put("imageUrl", vop.get("img"));
                if (button.equals("basBut")) {
                    obj2.put("buttons", arr2);
                    arr2.add(obj4);
                    arr2.add(obj5);
                    obj4.put("messageText", vop.get("firstMessage"));
                    obj4.put("label", vop.get("firstMessage"));
                    obj4.put("action", "message");
                    obj5.put("messageText", vop.get("exitMessage"));
                    obj5.put("label", vop.get("exitMessage"));
                    obj5.put("action", "message");
                } else if (button.equals("twoWeb")) {
                    obj2.put("buttons", arr2);
                    arr2.add(obj4);
                    arr2.add(obj5);
                    obj4.put("webLinkUrl", vop.get("url"));
                    obj4.put("label", vop.get("firstMessage"));
                    obj4.put("action", "webLink");
                    obj5.put("messageText", vop.get("secondMessage"));
                    obj5.put("label", vop.get("secondMessage"));
                    obj5.put("action", "message");
                } else if (button.equals("threeWeb")) {
                    obj2.put("buttons", arr2);
                    arr2.add(obj4);
                    arr2.add(obj5);
                    arr2.add(obj6);
                    obj4.put("webLinkUrl", vop.get("url"));
                    obj4.put("label", vop.get("firstMessage"));
                    obj4.put("action", "webLink");
                    obj5.put("messageText", vop.get("secondMessage"));
                    obj5.put("label", vop.get("secondMessage"));
                    obj5.put("action", "message");
                    obj6.put("webLinkUrl", vop.get("thirdUrl"));
                    obj6.put("label", vop.get("thirdMessage"));
                    obj6.put("action", "webLink");
                }
                return obj;
            } else if (format.equals("car")) {
                System.out.println("케러셀 진입");
                obj1.put("carousel", obj2);
                obj2.put("type", "basicCard");
                obj2.put("items", arr2);
                arr2.add(obj3);
                obj3.put("title", vop.get("title"));
                obj3.put("description", vop.get("description"));
                obj3.put("thumbnail", obj4);
                obj4.put("imageUrl", vop.get("img"));
                obj3.put("buttons", arr3);
                arr3.add(obj5);
                arr3.add(obj6);
                obj5.put("messageText", vop.get("firstMessage"));
                obj5.put("label", vop.get("firstMessage"));
                obj5.put("action", "message");
                obj6.put("messageText", vop.get("secondMessage"));
                obj6.put("label", vop.get("secondMessage"));
                obj6.put("action", "message");
                if (button.equals("examKind")) {
                    arr2.add(obj7);
                    obj7.put("title", vop.get("title"));
                    obj7.put("description", vop.get("description"));
                    obj7.put("thumbnail", obj4);
                    obj4.put("imageUrl", vop.get("img"));
                    obj7.put("buttons", arr4);
                    arr4.add(obj8);
                    arr4.add(obj9);
                    obj8.put("messageText", vop.get("thirdMessage"));
                    obj8.put("label", vop.get("thirdMessage"));
                    obj8.put("action", "message");
                    obj9.put("messageText", vop.get("fourthMessage"));
                    obj9.put("label", vop.get("fourthMessage"));
                    obj9.put("action", "message");
                } else if (button.equals("subjectKind")) {
                    System.out.println("준비중");
                }
                return obj;
            } else {
                logger.error("format logic ERROR");
                return null;
            }
        } catch (Exception e) {
            logger.error("format ERROR");
            return null;
        }
    }

    public Map<String, Object> fallbackSer(Map<String, Object> jsonParams) {
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
            vop.set(botUserKey, String.valueOf(chat.getChatId()));

            if (vop.get(botUserKey).equals("0")) {
                vop.set("adminCode", String.valueOf(chatMapper.selectUserList(392).getUserCode()));
                vop.set("userCode", String.valueOf(chatMapper.selectUserList(1186).getUserCode()));
                vop.set("adminLogin", chatMapper.selectUserList(392).getUserId() + ", " + chatMapper.selectUserList(392).getUserPw());
                vop.set("userLogin", chatMapper.selectUserList(1186).getUserId() + ", " + chatMapper.selectUserList(1186).getUserPw());
                if (chatBody.contains("로그인")) {
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
                if (chatBody.contains("로그인") || chatBody.equals(vop.get("adminLogin")) || chatBody.equals(vop.get("userLogin"))) {
                    return fallback.fallback(jsonParams);
                } else if (chatBody.contains("메뉴")) {
                    return this.menuSer(jsonParams);
                } else if (chatBody.contains("스트리밍") || chatBody.contains("화상교육") || chatBody.contains("강의")
                        || chatBody.contains("출석") || chatBody.contains("출결")) {
                    return this.classSer(jsonParams);
                } else if (chatBody.contains("오답노트") || chatBody.contains("시험분석")) {
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
            vop.set("text", "피클봇은 로그인 후에 사용하실 수 있습니다.\n아이디와 비밀번호를 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
            return this.addJson("sim", null);
        } catch (Exception e) {
            logger.error("로그인입력 ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> menuSer(Map<String, Object> jsonParams) {
        try {
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
            vop.set("title", "메뉴를 선택해주세요.");

            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                vop.set("description", (chatMapper.selectUserList(392).getName() + "님, 반갑습니다.\n피클봇을 종료하시려면 '종료'를 입력해주세요."));
                vop.set("img", "https://i.pinimg.com/564x/c3/d1/42/c3d1428151d94f028e3c9d1e5d86ea8f.jpg");
                vop.set("firstMessage", "출석관리");
                vop.set("url", "https://www.naver.com");
                vop.set("secondMessage", "시험분석");
                vop.set("thirdUrl", "https://www.naver.com");
                vop.set("thirdMessage", "화상교육");
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                vop.set("description", (chatMapper.selectUserList(1186).getName() + "님, 반갑습니다."));
                vop.set("img", "https://i.pinimg.com/564x/cc/8e/08/cc8e083f2c13532a94038138a6713ec1.jpg");
                vop.set("firstMessage", "출석체크 & 화상교육");
                vop.set("url", "https://www.naver.com");
                vop.set("secondMessage", "오답노트");
                vop.set("thirdMessage", vop.get("exitMessage"));
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("메뉴선택");
                chatHistoryMapper.insertData(chatHistory);
            }
            return this.addJson("bas", "threeWeb");
        } catch (Exception e) {
            logger.error("menu logic ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> classSer(Map<String, Object> jsonParams) {
        try {
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

            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                if (chatBody.contains("스트리밍") || chatBody.contains("교육") || chatBody.contains("강의")) {
                    vop.set("title", "화상교육");
                    vop.set("description", "화상교육 창으로 이동합니다.");
                    vop.set("img", "https://i.pinimg.com/564x/df/9b/65/df9b65458c037b6f9eac74e9035c62ad.jpg");
                    vop.set("url", "https://www.naver.com");
                } else if (chatBody.contains("출결") || chatBody.contains("출석")) {
                    vop.set("title", "출결관리");
                    vop.set("description", "출결관리 창으로 이동합니다.");
                    vop.set("img", "https://i.pinimg.com/564x/82/e5/db/82e5dbfb38770d47232532c5c5e3c932.jpg");
                    vop.set("url", "https://www.naver.com");
                    vop.set("firstMessage", vop.get("title"));
                } else {
                    logger.error("admin classSer logic ERROR");
                }
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                vop.set("title", "출석체크 & 화상교육");
                vop.set("description", "출석은 화상교육 창에 진입 시 자동으로 체크됩니다. 화상교육으로 이동하기!");
                vop.set("img", "https://i.pinimg.com/564x/82/e5/db/82e5dbfb38770d47232532c5c5e3c932.jpg");
                vop.set("url", "https://www.naver.com");
                vop.set("firstMessage", vop.get("title"));
            }
            vop.set("firstMessage", vop.get("title"));
            vop.set("secondMessage", vop.get("exitMessage"));
            chatHistory.setChatKind("B");
            chatHistory.setChatBody(vop.get("title").toString());
            chatHistoryMapper.insertData(chatHistory);
            return this.addJson("bas", "twoWeb");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("classSer logic ERROR");
            return null;
        }
    }

    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
        try {
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
            arr3 = new JSONArray();
            JSONArray arr4 = new JSONArray();
            JSONArray arr5 = new JSONArray();
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            //test
            String[] subjects = {"국어", "수학", "영어", "경제", "한국사", "생활과윤리", "물리", "생명과학", "제2외국어"};
            vop.set("firstSem", "1학기");
            vop.set("secondSem", "2학기");
            vop.set("midtermExm", "중간고사");
            vop.set("finalExm", "기말고사");

            chat.setChatBody(chatBody);
            chatMapper.updateData(chat);
            chatHistory.setChatId(chat.getChatId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            String analyses = null;
            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                analyses = "시험분석";
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                analyses = "오답노트";
            } else {
                logger.error("analyses 사용자구분 ERROR");
            }
            if (chatBody.contains(analyses)) {
                ArrayList<ChatHistory> histories = chatHistoryMapper.selectList(chat.getChatId());
                Iterator itr = histories.iterator();
                while (itr.hasNext()) {
                    if (itr.next().toString().contains(vop.get("firstSem").toString()) && itr.next().toString().contains(vop.get("midtermExam").toString())) {
                        System.out.println("");
                    }
                }
                String[] historiesStr = histories.toString().split(",");
                System.out.println(Arrays.asList(historiesStr));
                for (int i = 0; i < historiesStr.length; i++) {
                    if (historiesStr[i].contains(vop.get("firstSem").toString()) && historiesStr[i].contains(vop.get("midtermExam").toString())
                            || historiesStr[i].contains(vop.get("firstSem").toString()) && historiesStr[i].contains(vop.get("finalExam").toString())
                            || historiesStr[i].contains(vop.get("secondSem").toString()) && historiesStr[i].contains(vop.get("midtermExam").toString())
                            || historiesStr[i].contains(vop.get("secondSem").toString()) && historiesStr[i].contains(vop.get("finalExam").toString())) {
                        if (historiesStr[i].contains(vop.get("kor").toString()) || historiesStr[i].contains(vop.get("eng").toString())
                                || historiesStr[i].contains(vop.get("mat").toString()) || historiesStr[i].contains(vop.get("eco").toString())
                                || historiesStr[i].contains(vop.get("his").toString()) || historiesStr[i].contains(vop.get("phl").toString())
                                || historiesStr[i].contains(vop.get("phy").toString()) || historiesStr[i].contains(vop.get("bio").toString())
                                || historiesStr[i].contains(vop.get("for").toString())) {
                            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
//                        Exam examList = examMapper.selectList();
//                        title = chatBody + examList.getExamNum()
                                description = "[정답: 4번]\n-학생들이 가장 많이 선택한 오답: 2번\n어휘의 형성 체계가 가장 다른 것은?\n1. 손쉽다   2. 맛나다\n3. 시름없다   4. 남다르다";
                                img = adminAnImg;
                                obj.put("version", "2.0");
                                obj.put("template", arrObj);
                                arrObj.put("outputs", arr);
                                arr.add(obj1);
                                obj1.put("basicCard", obj3);
                            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                                title = null;
                                description = "[정답: 2, 4번]\n-내가 선택한 오답: 3번\n잘못 짝지어진 것은?\n1. 들: ㄷㄹ-   2. 뜻: ㄷㅇㅅ-\n3. 생각: ㅅ-   4. 뿐: ㅈㅂㄹ-";
                                img = userAnImg;
                            }
                            obj3.put("title", title);
                            obj3.put("description", description);
                            obj3.put("thumbnail", obj4);
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
                            title = "과목종류선택";
                            description = "과목종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.";
                            img = subjectCodeImg;
                            obj3.put("title", title);
                            obj3.put("description", description);
                            obj3.put("thumbnail", obj4);
                            obj3.put("buttons", arr3);
                            obj4.put("imageUrl", img);
                            arr3.add(obj5);
                            arr3.add(obj6);
                            arr3.add(obj7);

                            obj5.put("messageText", vop.get("kor"));
                            obj5.put("label", vop.get("kor"));
                            obj5.put("action", "message");
                            obj6.put("messageText", vop.get("eng"));
                            obj6.put("label", vop.get("eng"));
                            obj6.put("action", "message");
                            obj7.put("messageText", vop.get("mat"));
                            obj7.put("label", vop.get("mat"));
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
                            obj9.put("messageText", vop.get("eco"));
                            obj9.put("label", vop.get("eco"));
                            obj9.put("action", "message");
                            obj10.put("messageText", vop.get("his"));
                            obj10.put("label", vop.get("his"));
                            obj10.put("action", "message");
                            obj11.put("messageText", vop.get("phl"));
                            obj11.put("label", vop.get("phl"));
                            obj11.put("action", "message");

                            arr2.add(obj12);
                            obj12.put("title", title);
                            obj12.put("description", description);
                            obj12.put("thumbnail", obj4);
                            obj12.put("buttons", arr5);
                            obj4.put("imageUrl", subjectCodeImg);
                            arr5.add(obj13);
                            arr5.add(obj14);
                            arr5.add(obj15);
                            obj13.put("messageText", vop.get("phy"));
                            obj13.put("label", vop.get("phy"));
                            obj13.put("action", "message");
                            obj14.put("messageText", vop.get("bio"));
                            obj14.put("label", vop.get("bio"));
                            obj14.put("action", "message");
                            obj15.put("messageText", vop.get("for"));
                            obj15.put("label", vop.get("for"));
                            obj15.put("action", "message");
                            chatHistory.setChatKind("B");
                            chatHistory.setChatBody(title);
                            chatHistoryMapper.insertData(chatHistory);
                            return obj;
                        }
                    } else {
                        //여기야여기
                        vop.set("title", "시험종류선택");
                        vop.set("description", "시험종류를 선택해주세요.\n챗봇을 종료하시려면 '종료'를 입력해주세요.");
                        vop.set("img", "https://i.pinimg.com/564x/81/21/12/8121120d992f697528c3ca99d9af4baa.jpg");
                        vop.set("firstMessage", (vop.get("firstSem") + " " + vop.get("midtermExm")));
                        vop.set("secondMessage", (vop.get("firstSem") + " " + vop.get("finalExm")));
                        vop.set("thirdMessage", (vop.get("secondSem") + " " + vop.get("midtermExm")));
                        vop.set("fourthMessage", (vop.get("secondSem") + " " + vop.get("finalExm")));

                        chatHistory.setChatKind("B");
                        chatHistory.setChatBody(vop.get("title").toString());
                        chatHistoryMapper.insertData(chatHistory);
                        return this.addJson("car", "examKind");
                    }
                }
                logger.error("여긴 머야?");
                return null;
            } else {
                logger.info("analysis 발화 부족으로 fallback");
                return fallback.fallback(jsonParams);
            }
        } catch (Exception e) {
            logger.error("analysis logic ERROR");
            e.printStackTrace();
            return null;
        }
    }
}