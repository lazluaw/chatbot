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
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, obj14, obj15, arrObj;
    private JSONArray arr, arr2, arr3, arr4, arr5;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String jsonStr;
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
            obj10 = new JSONObject();
            obj11 = new JSONObject();
            obj12 = new JSONObject();
            obj13 = new JSONObject();
            obj14 = new JSONObject();
            obj15 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();
            arr3 = new JSONArray();
            arr4 = new JSONArray();
            arr5 = new JSONArray();
            vop = redisTemplate.opsForValue();
            vop.set("exitMes", "챗봇종료");
            if (format.equals("sim")) {
                System.out.println("sim 진입");
                obj2.put("text", vop.get("text"));
                obj1.put("simpleText", obj2);
                //test: 챗봇 종료가 안 되는 상황
            } else if (format.equals("bas")) {
                System.out.println("bas 진입");
                obj3.put("imageUrl", vop.get("img"));
                obj2.put("thumbnail", obj3);
                obj2.put("description", vop.get("description"));
                obj2.put("title", vop.get("title"));
                obj1.put("basicCard", obj2);
                if (button.equals("basBut")) {
                    System.out.println("basicBut 진입");
                    obj5.put("action", "message");
                    obj5.put("label", vop.get("firstMes"));
                    obj5.put("messageText", vop.get("firstMes"));
                    obj4.put("action", "message");
                    obj4.put("label", vop.get("exitMes"));
                    obj4.put("messageText", vop.get("exitMes"));
                    arr2.add(obj5);
                    arr2.add(obj4);
                    obj2.put("buttons", arr2);
                    //test: 고도화 필요
                } else if (button.equals("web")) {
                    System.out.println("web진입");
                    obj5.put("action", "message");
                    obj5.put("label", vop.get("firstMes"));
                    obj5.put("messageText", vop.get("firstMes"));
                    obj4.put("action", "webLink");
                    obj4.put("label", vop.get("secondMes"));
                    obj4.put("webLinkUrl", vop.get("secondUrl"));
                    arr2.add(obj5);
                    arr2.add(obj4);
                    obj2.put("buttons", arr2);
                } else if (button.equals("addWeb")) {
                    System.out.println("세번째 메뉴 진입");
                    obj6.put("action", "webLink");
                    obj6.put("label", vop.get("thirdMes"));
                    obj6.put("webLinkUrl", vop.get("thirdUrl"));
                    obj5.put("action", "message");
                    obj5.put("label", vop.get("firstMes"));
                    obj5.put("messageText", vop.get("firstMes"));
                    obj4.put("action", "webLink");
                    obj4.put("label", vop.get("secondMes"));
                    obj4.put("webLinkUrl", vop.get("secondUrl"));
                    arr2.add(obj6);
                    arr2.add(obj5);
                    arr2.add(obj4);
                    obj2.put("buttons", arr2);
                }
            } else if (format.equals("car")) {
                System.out.println("케러셀 진입");
                obj6.put("action", "message");
                obj6.put("label", vop.get("secondMes"));
                obj6.put("messageText", vop.get("secondMes"));
                obj5.put("action", "message");
                obj5.put("label", vop.get("firstMes"));
                obj5.put("messageText", vop.get("firstMes"));
                arr3.add(obj6);
                arr3.add(obj5);
                obj3.put("buttons", arr3);
                obj4.put("imageUrl", vop.get("img"));
                obj3.put("thumbnail", obj4);
                obj3.put("description", vop.get("description"));
                obj3.put("title", vop.get("title"));
                arr2.add(obj3);
                obj2.put("items", arr2);
                obj2.put("type", "basicCard");
                obj1.put("carousel", obj2);
                if (button.equals("examKind")) {
                    System.out.println("examkind");
                    obj9.put("action", "message");
                    obj9.put("label", vop.get("fourthMes"));
                    obj9.put("messageText", vop.get("fourthMes"));
                    obj8.put("action", "message");
                    obj8.put("label", vop.get("thirdMes"));
                    obj8.put("messageText", vop.get("thirdMes"));
                    arr4.add(obj9);
                    arr4.add(obj8);
                    obj7.put("buttons", arr4);
                    obj7.put("thumbnail", obj4);
                    obj4.put("imageUrl", vop.get("img"));
                    obj7.put("description", vop.get("description"));
                    obj7.put("title", vop.get("title"));
                    arr2.add(obj7);
                } else if (button.equals("subjectKind")) {
                    System.out.println("subject 진입");
                    obj7.put("action", "message");
                    obj7.put("label", vop.get("thirdMes"));
                    obj7.put("messageText", vop.get("thirdMes"));
                    arr3.add(obj7);

                    obj15.put("action", "message");
                    obj15.put("label", vop.get("fourthMes"));
                    obj15.put("messageText", vop.get("fourthMes"));
                    obj14.put("action", "message");
                    obj14.put("label", vop.get("fifthMes"));
                    obj14.put("messageText", vop.get("fifthMes"));
                    obj13.put("action", "message");
                    obj13.put("label", vop.get("sixthMes"));
                    obj13.put("messageText", vop.get("sixthMes"));
                    arr5.add(obj15);
                    arr5.add(obj14);
                    arr5.add(obj13);
                    obj4.put("imageUrl", vop.get("img"));
                    obj12.put("buttons", arr5);
                    obj12.put("thumbnail", obj4);
                    obj12.put("description", vop.get("description"));
                    arr2.add(obj12);
                    obj12.put("title", vop.get("title"));

                    obj11.put("action", "message");
                    obj11.put("label", vop.get("seventhMes"));
                    obj11.put("messageText", vop.get("seventhMes"));
                    obj10.put("action", "message");
                    obj10.put("label", vop.get("eightthMes"));
                    obj10.put("messageText", vop.get("eigththMes"));
                    obj9.put("action", "message");
                    obj9.put("label", vop.get("ninthMes"));
                    obj9.put("messageText", vop.get("ninthMes"));
                    arr4.add(obj11);
                    arr4.add(obj10);
                    arr4.add(obj9);
                    obj4.put("imageUrl", vop.get("img"));
                    obj8.put("buttons", arr4);
                    obj8.put("thumbnail", obj4);
                    obj8.put("description", vop.get("description"));
                    obj8.put("title", vop.get("title"));
                    arr2.add(obj8);
                }
            }
            arr.add(obj1);
            arrObj.put("outputs", arr);
            obj.put("template", arrObj);
            obj.put("version", "2.0");
            return obj;
        } catch (Exception e) {
            logger.error("format ERROR");
            return null;
        }
    }

    public void addData(String division, String data) {
        chatHistory.setChatKind("B");
        chatHistory.setChatBody(data);
        chatHistoryMapper.insertData(chatHistory);
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
                    return this.loginSer();
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
                vop.set("firstMes", "시험분석");
                vop.set("secondMes", "출결관리");
                vop.set("secondUrl", "https://www.naver.com");
                vop.set("thirdMes", "화상교육");
                vop.set("thirdUrl", "https://www.naver.com");
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("메뉴선택");
                chatHistoryMapper.insertData(chatHistory);
                return this.addJson("bas", "addWeb");
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                vop.set("description", (chatMapper.selectUserList(1186).getName() + "님, 반갑습니다.\n피클봇을 종료하시려면 '종료'를 입력해주세요."));
                vop.set("img", "https://i.pinimg.com/564x/cc/8e/08/cc8e083f2c13532a94038138a6713ec1.jpg");
                vop.set("firstMes", "오답노트");
                vop.set("secondMes", "출석체크 & 화상교육");
                vop.set("secondUrl", "https://www.naver.com");
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("메뉴선택");
                chatHistoryMapper.insertData(chatHistory);
                return this.addJson("bas", "web");
            } else {
                logger.error("menu ERROR");
                return null;
            }
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
                    vop.set("firstMes", vop.get("title"));
                } else {
                    logger.error("admin classSer logic ERROR");
                }
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                vop.set("title", "출석체크 & 화상교육");
                vop.set("description", "출석은 화상교육 창에 진입 시 자동으로 체크됩니다. 화상교육으로 이동하기!");
                vop.set("img", "https://i.pinimg.com/564x/82/e5/db/82e5dbfb38770d47232532c5c5e3c932.jpg");
                vop.set("url", "https://www.naver.com");
                vop.set("firstMes", vop.get("title"));
            }
            vop.set("firstMes", vop.get("title"));
            vop.set("secondMes", vop.get("exitMes"));
            chatHistory.setChatKind("B");
            chatHistory.setChatBody(vop.get("title").toString());
            chatHistoryMapper.insertData(chatHistory);
            return this.addJson("bas", "web");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("classSer logic ERROR");
            return null;
        }
    }

    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
        try {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            //test
            vop.set("firstSem", "1학기");
            vop.set("secondSem", "2학기");
            vop.set("midtermExm", "중간고사");
            vop.set("finalExm", "기말고사");
            vop.set("kor", "국어");
            vop.set("eng", "영어");
            vop.set("mat", "수학");
            vop.set("eco", "경제");
            vop.set("his", "한국사");
            vop.set("phl", "생활과윤리");
            vop.set("phy", "물리");
            vop.set("bio", "생명과학");
            vop.set("for", "제2외국어");

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

            //여기 해야 함
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
                                vop.set("description", "[정답: 4번]\n-학생들이 가장 많이 선택한 오답: 2번\n어휘의 형성 체계가 가장 다른 것은?\n1. 손쉽다   2. 맛나다\n3. 시름없다   4. 남다르다");
                                vop.set("img", "https://i.pinimg.com/564x/e7/c3/cc/e7c3cc68c94b5828e347c0e35255425d.jpg");
                            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                                vop.set("description", "[정답: 2, 4번]\n-내가 선택한 오답: 3번\n잘못 짝지어진 것은?\n1. 들: ㄷㄹ-   2. 뜻: ㄷㅇㅅ-\n3. 생각: ㅅ-   4. 뿐: ㅈㅂㄹ-");
                                vop.set("img", "https://i.pinimg.com/564x/7e/0a/70/7e0a70b7dc579def017a4cd56ad826f9.jpg");
                            }
                            vop.set("title", (analyses+"분석결과"));
                            vop.set("firstMes", "메뉴로 이동");

                            chatHistory.setChatKind("B");
                            chatHistory.setChatBody(analyses);
                            chatHistoryMapper.insertData(chatHistory);
                            return this.addJson("bas", "basBut");
                        } else {
                            vop.set("title", "과목종류선택");
                            vop.set("description", "과목종류를 선택해주세요.\n피클봇을 종료하시려면 '종료'를 입력해주세요.");
                            vop.set("img", "https://i.pinimg.com/564x/9a/48/f4/9a48f4460b011bf688e50c03165dadd6.jpg");
                            vop.set("firstMes", vop.get("kor"));
                            vop.set("secondMes", vop.get("eng"));
                            vop.set("thirdMes", vop.get("mat"));
                            vop.set("fourthMes", vop.get("eco"));
                            vop.set("fifthMes", vop.get("his"));
                            vop.set("sixthMes", vop.get("phl"));
                            vop.set("seventhMes", vop.get("phy"));
                            vop.set("eightthMes", vop.get("bio"));
                            vop.set("ninethMes", vop.get("for"));

                            chatHistory.setChatKind("B");
                            chatHistory.setChatBody(vop.get("title").toString());
                            chatHistoryMapper.insertData(chatHistory);
                            return this.addJson("car", "subjectKind");
                        }
                    } else {
                        vop.set("title", "시험종류선택");
                        vop.set("description", "시험종류를 선택해주세요.\n피클봇을 종료하시려면 '종료'를 입력해주세요.");
                        vop.set("img", "https://i.pinimg.com/564x/81/21/12/8121120d992f697528c3ca99d9af4baa.jpg");
                        vop.set("firstMes", (vop.get("firstSem") + " " + vop.get("midtermExm")));
                        vop.set("secondMes", (vop.get("firstSem") + " " + vop.get("finalExm")));
                        vop.set("thirdMes", (vop.get("secondSem") + " " + vop.get("midtermExm")));
                        vop.set("fourthMes", (vop.get("secondSem") + " " + vop.get("finalExm")));

                        chatHistory.setChatKind("B");
                        chatHistory.setChatBody(vop.get("title").toString());
                        chatHistoryMapper.insertData(chatHistory);
                        return this.addJson("car", "examKind");
                    }
                }
                logger.error("analysis 사용자구분 ERROR");
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