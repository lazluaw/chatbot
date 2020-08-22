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
    private static ObjectMapper mapper = new JsonMapper();
    private JSONParser parser = new JSONParser();
    private JSONObject obj, obj0, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, arrObj;
    private JSONArray arr, arr0, arr1, arr2, arr3, arr4;
    private ValueOperations<String, Object> vop;
    private String jsonStr, title, title1, url, img, img1, chatBody;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Object> addJson(String format, String button) {
        try {
            obj = new JSONObject();
            obj0 = new JSONObject();
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
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr0 = new JSONArray();
            arr1 = new JSONArray();
            arr2 = new JSONArray();
            arr3 = new JSONArray();
            arr4 = new JSONArray();
            vop = redisTemplate.opsForValue();
            if (format.equals("sim")) {
                System.out.println("sim 진입");
                obj2.put("text", vop.get("text"));
                obj1.put("simpleText", obj2);
                if (button.equals("qBut")) {
                    System.out.println("qBut 진입");
                    if (chatHistory.getChatBody().contains("시험종류")) {
                        obj6.put("message", vop.get("fourthMes"));
                        obj6.put("label", vop.get("fourthMes"));
                        obj6.put("action", "message");
                        arr0.add(obj6);
                    } else if (chatHistory.getChatBody().equals("과목종류")) {
                        obj11.put("message", vop.get("phl"));
                        obj11.put("label", vop.get("phl"));
                        obj11.put("action", "message");
                        arr0.add(obj11);
                        obj10.put("message", vop.get("his"));
                        obj10.put("label", vop.get("his"));
                        obj10.put("action", "message");
                        arr0.add(obj10);
                        obj9.put("message", vop.get("mat"));
                        obj9.put("label", vop.get("mat"));
                        obj9.put("action", "message");
                        arr0.add(obj9);
                        obj8.put("message", vop.get("eng"));
                        obj8.put("label", vop.get("eng"));
                        obj8.put("action", "message");
                        arr0.add(obj8);
                        obj7.put("message", vop.get("kor"));
                        obj7.put("label", vop.get("kor"));
                        obj7.put("action", "message");
                        arr0.add(obj7);
                        obj6.put("message", vop.get("fourthMes"));
                        obj6.put("label", vop.get("fourthMes"));
                        obj6.put("action", "message");
                        arr0.add(obj6);
                    }
                    obj5.put("message", vop.get("thirdMes"));
                    obj5.put("label", vop.get("thirdMes"));
                    obj5.put("action", "message");
                    arr0.add(obj5);
                    obj4.put("message", vop.get("secondMes"));
                    obj4.put("label", vop.get("secondMes"));
                    obj4.put("action", "message");
                    arr0.add(obj4);
                    obj3.put("message", vop.get("firstMes"));
                    obj3.put("label", vop.get("firstMes"));
                    obj3.put("action", "message");
                    arr0.add(obj3);
                } else {
                    System.out.println(obj3.toString());
                    logger.error("qBut ERROR");
                }
            } else if (format.equals("list")) {
                System.out.println("list 진입");
                obj9.put("messageText", vop.get("button"));
                obj9.put("label", vop.get("button"));
                obj9.put("action", "message");
                arr2.add(obj9);
                obj2.put("buttons", arr2);

                obj8.put("web", vop.get("thirdUrl"));
                obj7.put("link", obj8);
                obj7.put("imageUrl", vop.get("thirdImg"));
                obj7.put("description", vop.get("thirdD"));
                obj7.put("title", vop.get("thirdT"));
                arr1.add(obj7);

                obj6.put("web", vop.get("secondUrl"));
                obj5.put("link", obj6);
                obj5.put("imageUrl", vop.get("secondImg"));
                obj5.put("description", vop.get("secondD"));
                obj5.put("title", vop.get("secondT"));
                arr1.add(obj5);

                obj4.put("imageUrl", vop.get("firstImg"));
                obj4.put("description", vop.get("firstD"));
                obj4.put("title", vop.get("firstT"));
                arr1.add(obj4);

                obj2.put("items", arr1);
                obj3.put("imageUrl", vop.get("img"));
                obj3.put("title", vop.get("title"));
                obj2.put("header", obj3);
                obj1.put("listCard", obj2);
            } else if (format.equals("car")) {
                System.out.println("케러셀 진입");
                obj5.put("action", vop.get("kind"));
                obj5.put("label", vop.get("firstMes"));
                obj5.put("messageText", vop.get("firstMes"));
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
                if (button.equals("qBut")) {
                    System.out.println("qBut 진입");
                } else {
                    logger.error("car ERROR");
                }
            }
            obj0.put("message", vop.get("lastMes"));
            obj0.put("label", vop.get("lastMes"));
            obj0.put("action", "message");
            arr0.add(obj0);
            arrObj.put("quickReplies", arr0);

            arr.add(obj1);
            arrObj.put("outputs", arr);
            obj.put("template", arrObj);
            obj.put("version", "2.0");
            System.out.println(obj);
            return obj;
        } catch (Exception e) {
            logger.error("format ERROR");
            return null;
        }
    }

    public void addData(Map<String, Object> jsonParams, String division, String data) {
        try {
            System.out.println("addData 진입");
            mapper = new ObjectMapper();
            parser = new JSONParser();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();
            if (division.equals("c")) {
                System.out.println("C addData");
                chatHistory.setChatId(chat.getChatId());
                chatHistory.setChatKind("C");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(chatBody);
                chatHistoryMapper.insertData(chatHistory);
            } else if (division.equals("b")) {
                if (data != null) {
                    System.out.println("B chatHistory addData");
                    chatHistory.setChatKind("B");
                    chatHistory.setChatBody(data);
                    chatHistoryMapper.insertData(chatHistory);
                } else {
                    System.out.println("B chat addData");
                    chat.setChatBody(chatBody);
                    chat.setUserCode(chat.getUserCode());
                    chatMapper.insertData(chat);
                }
            }
        } catch (Exception e) {
            logger.error("addData ERROR");
            e.printStackTrace();
        }
    }

    public String parsing(Map<String, Object> jsonParams) {
        try {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            return chatBody;
        } catch (Exception e) {
            logger.error("parsing ERROR");
            return null;
        }
    }

    public Map<String, Object> controlSer(Map<String, Object> jsonParams) {
        try {
            chatBody = this.parsing(jsonParams);
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, String.valueOf(chat.getChatId()));
            vop.set("lastMes", "종료");
            String[] exams = {"1학기 중간고사", "1학기 기말고사", "2학기 중간고사", "2학기 기말고사"};
            String[] subjects = {"국어", "영어", "수학", "생활과윤리", "한국사", "생명과학", "제2외국어", "경제", "물리"};
            if (vop.get(botUserKey).equals("0")) {
                vop.set("adminCode", String.valueOf(chatMapper.selectUserList(392).getUserCode()));
                vop.set("userCode", String.valueOf(chatMapper.selectUserList(1186).getUserCode()));
                vop.set("adminLogin", chatMapper.selectUserList(392).getUserId() + ", " + chatMapper.selectUserList(392).getUserPw());
                vop.set("userLogin", chatMapper.selectUserList(1186).getUserId() + ", " + chatMapper.selectUserList(1186).getUserPw());
                if (chatBody.contains("로그인")) {
                    return this.loginSer();
                } else if (chatBody.equals(vop.get("adminLogin"))) {
                    logger.info("admin login insert");
                    chat.setUserCode(Integer.parseInt(vop.get("adminCode").toString()));
                    this.addData(jsonParams, "b", null);
                    return this.menuSer(jsonParams);
                } else if (chatBody.equals(vop.get("userLogin"))) {
                    logger.info("user login insert");
                    chat.setUserCode(Integer.parseInt(vop.get("userCode").toString()));
                    this.addData(jsonParams, "b", null);
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
                    vop.set("overlap", "1");
                    return fallback.fallback(jsonParams);
                } else if (chatBody.contains("메뉴")) {
                    return this.menuSer(jsonParams);
                } else if (chatBody.contains("화상") || chatBody.contains("강의") || chatBody.contains("출석") || chatBody.contains("출결")) {
                    return this.classSer(jsonParams);
                } else if (chatBody.contains("오답노트") || Arrays.asList(exams).contains(chatBody) || Arrays.asList(subjects).contains(chatBody)) {
                    return this.examSer(jsonParams);
                } else if (chatBody.contains("시험분석")) {
                    return this.analysisSer(jsonParams);
                } else if (chatBody.contains("종료")) {
                    return exit.exit(jsonParams);
                } else {
                    logger.info("로그인 fallback");
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
            vop.set("lastMes", "");
            vop.set("text", "피클봇은 로그인 후에 사용하실 수 있습니다.\n아이디와 비밀번호를 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
            return this.addJson("sim", "0");
        } catch (Exception e) {
            logger.error("로그인입력 ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> menuSer(Map<String, Object> jsonParams) {
        try {
            vop.set("title", "메뉴를 선택해주세요.");
            vop.set("img", "https://cdn.pixabay.com/photo/2014/05/03/00/46/notebook-336634_1280.jpg");
            vop.set("thirdT", "화상교육");
            vop.set("thirdD", "다대면 화상교육을 진행합니다.");
            vop.set("thirdImg", "https://i.pinimg.com/564x/fe/d2/d7/fed2d7cc7ffab16d06138b226735dfae.jpg");
            vop.set("thirdUrl", "https://www.naver.com");
            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                title = "출결관리";
                img = "https://i.pinimg.com/564x/3c/02/af/3c02afb940e4aa0e7c9448d75ee0f04f.jpg";
                url = "https://www.naver.com";
                title1 = "시험분석";
                img1 = "https://i.pinimg.com/564x/59/e0/28/59e0283e88f18b5c35ed59dbdb869b8d.jpg";
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                title = "출석체크";
                img = "https://i.pinimg.com/564x/3c/02/af/3c02afb940e4aa0e7c9448d75ee0f04f.jpg";
                url = "https://www.naver.com";
                title1 = "오답노트";
                img1 = "https://i.pinimg.com/564x/3f/fb/3b/3ffb3ba83f1be537725827d331452695.jpg";
            } else {
                logger.error("menu ERROR");
                return null;
            }
            vop.set("secondT", title);
            vop.set("secondD", title + " 진행합니다.");
            vop.set("secondImg", img);
            vop.set("secondUrl", url);
            vop.set("firstT", title1);
            vop.set("firstD", title1 + " 진행합니다.");
            vop.set("firstImg", img1);
            vop.set("button", title1);
            //chat: 로그인 후에 바로 메뉴에 왔을 때 데이터가 이중으로 저장되어도 어쩔 수 없..을까?
            this.addData(jsonParams, "b", null);
            this.addData(jsonParams, "c", null);
            this.addData(jsonParams, "b", "대메뉴");
            return this.addJson("list", "0");
        } catch (Exception e) {
            logger.error("menu logic ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> classSer(Map<String, Object> jsonParams) {
        try {
            chatBody = this.parsing(jsonParams);
            if (chatBody.contains("화상") || chatBody.contains("강의")) {
                title = "화상교육";
                img = "https://cdn.pixabay.com/photo/2018/10/23/10/09/video-recording-3767454_1280.jpg";
                url = "https://www.naver.com";
            } else if (chatBody.contains("출결") || chatBody.contains("출석")) {
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출결관리";
                    url = "https://www.naver.com";
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출석체크";
                    url = "https://www.naver.com";
                }
                img = "https://media.istockphoto.com/photos/attendance-concept-on-a-computer-display-picture-id1161571987?s=2048x2048";
            } else {
                logger.error("attendance classSer logic ERROR");
            }
            vop.set("title", title);
            vop.set("description", title + " 진행합니다.");
            vop.set("img", img);
            vop.set("firstUrl", url);
            vop.set("firstMes", "진행하기");
            vop.set("kind", "webLink");

            this.addData(jsonParams, "b", null);
            this.addData(jsonParams, "c", null);
            this.addData(jsonParams, "b", title);
            return this.addJson("car", "0");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("classSer logic ERROR");
            return null;
        }
    }

    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
        try {
            vop.set("lastMes", "");
            chatBody = this.parsing(jsonParams);
            //test: 기준 세우기
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
            if (chatBody.contains(vop.get("firstSem").toString()) && chatBody.contains(vop.get("midtermExm").toString())
                    || chatBody.contains(vop.get("firstSem").toString()) && chatBody.contains(vop.get("finalExm").toString())
                    || chatBody.contains(vop.get("secondSem").toString()) && chatBody.contains(vop.get("midtermExm").toString())
                    || chatBody.contains(vop.get("secondSem").toString()) && chatBody.contains(vop.get("finalExm").toString())) {
                logger.info("과목종류 진입");
                vop.set("text", "과목종류를 선택해주세요.");
                vop.set("firstMes", vop.get("eco"));
                vop.set("secondMes", vop.get("phy"));
                vop.set("thirdMes", vop.get("bio"));
                vop.set("fourthMes", vop.get("for"));
                vop.set("subjectKind", chatBody);
                this.addData(jsonParams, "b", null);
                this.addData(jsonParams, "c", null);
                this.addData(jsonParams, "b", "과목종류");
                return this.addJson("sim", "qBut");
            } else if (chatBody == null) {
                System.out.println("오답노트");
                if (vop.get("examKind") != null && vop.get("subjectKind") != null) {
                    if (vop.get("examKind").toString().equals("1학기 중간고사")) {
                        exam.setExamKind(202001);
                        exam.setSubjectCode("");
                    } else if (vop.get("examKind").toString().equals("1학기 기말고사")) {
                        exam.setExamKind(202002);
                    } else if (vop.get("examKind").toString().equals("2학기 중간고사")) {
                        exam.setExamKind(202003);
                    } else if (vop.get("examKind").toString().equals("2학기 기말고사")) {
                        exam.setExamKind(202004);
                    }
                    vop.set("title", (vop.get("examKind") + " " + vop.get("subjectKind")));
                    vop.set("description", );
                    vop.set("img", )
                    vop.set("kind", "message");
                    this.addData(jsonParams, "b", null);
                    this.addData(jsonParams, "c", null);
                    this.addData(jsonParams, "b", "오답노트");
                    return this.addJson("car", "carBut");
                } else {
                    logger.info("발화부족 fallback");
                    return fallback.fallback(jsonParams);
                }
            } else {
                logger.error("user analysis logic ERROR");
            }
            vop.set("text", "시험종류를 선택해주세요.");
            vop.set("firstMes", (vop.get("secondSem") + " " + vop.get("finalExm")));
            vop.set("secondMes", (vop.get("secondSem") + " " + vop.get("midtermExm")));
            vop.set("thirdMes", (vop.get("firstSem") + " " + vop.get("finalExm")));
            vop.set("fourthMes", (vop.get("firstSem") + " " + vop.get("midtermExm")));
            vop.set("examKind", chatBody);
            this.addData(jsonParams, "b", null);
            this.addData(jsonParams, "c", null);
            this.addData(jsonParams, "b", "시험종류");
            return this.addJson("sim", "qBut");
        } catch (Exception e) {
            logger.error("analysis logic ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> analysisSer(Map<String, Object> jsonParams) {
        return null;
    }
}