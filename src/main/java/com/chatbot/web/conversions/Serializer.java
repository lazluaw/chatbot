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

import java.util.*;

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
    private JSONObject obj, obj0, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, arrObj;
    private JSONArray arr, arr0, arr1, arr2, arr3, arr4;
    private ValueOperations<String, Object> vop;
    private String jsonStr, title, title1, url, img, img1, division, chatBody;
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
            obj12 = new JSONObject();
            obj13 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr0 = new JSONArray();
            arr1 = new JSONArray();
            arr2 = new JSONArray();
            arr3 = new JSONArray();
            arr4 = new JSONArray();
            vop = redisTemplate.opsForValue();
            if (format.equals("sim")) {
                obj2.put("text", vop.get("text"));
                obj1.put("simpleText", obj2);
                if (button.equals("qBut")) {
                    if (chatHistory.getChatBody().contains("시험")) {
                        obj6.put("message", vop.get("fourthMes"));
                        obj6.put("label", vop.get("fourthMes"));
                        obj6.put("action", "message");
                        arr0.add(obj6);
                    } else if (chatHistory.getChatBody().contains("과목")) {
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
                    logger.error("qBut ERROR");
                }
            } else if (format.equals("list")) {
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
                obj5.put("action", vop.get("kind"));
                obj5.put("label", vop.get("firstMes"));
                obj5.put(vop.get("mesKind"), vop.get("firstUrl"));
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
                    obj7.put("message", vop.get("thirdMes"));
                    obj7.put("label", vop.get("thirdMes"));
                    obj7.put("action", "message");
                    arr0.add(obj7);
                    obj6.put("message", vop.get("secondMes"));
                    obj6.put("label", vop.get("secondMes"));
                    obj6.put("action", "message");
                    arr0.add(obj6);
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
            return obj;
        } catch (Exception e) {
            logger.error("format ERROR");
            return null;
        }
    }

    public String parsing(Map<String, Object> jsonParams, String kind) {
        try {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            if (kind.equals("userInfo")) {
                JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
                return userRequest.toString();
            } else if (kind.equals("chatBody")) {
                JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
                return userRequest.get("utterance").toString();
            } else if (kind.equals("division")) {
                JSONObject action = (JSONObject) jsonPar.get("action");
                return action.get("name").toString();
            } else if (kind.equals("botUserKey")) {
                JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
                JSONObject user = (JSONObject) userRequest.get("user");
                JSONObject properties = (JSONObject) user.get("properties");
                return properties.get("botUserKey").toString();
            } else {
                logger.error("pasring if절 ERROR");
                return null;
            }
        } catch (Exception e) {
            logger.error("parsing ERROR");
            return null;
        }
    }

    public void addData(Map<String, Object> jsonParams, String division, String data) {
        try {
            chatBody = this.parsing(jsonParams, "chatBody");
            String userInfo = this.parsing(jsonParams, "userInfo");
            if (division.equals("c")) {
                chatHistory.setChatId(chat.getChatId());
                chatHistory.setChatKind("C");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(chatBody);
                chatHistoryMapper.insertData(chatHistory);
            } else if (division.equals("b")) {
                if (data != null) {
                    chatHistory.setChatKind("B");
                    chatHistory.setChatBody(data);
                    chatHistoryMapper.insertData(chatHistory);
                } else {
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

    public Map<String, Object> checkSer(Map<String, Object> jsonParams) {
        try {
            chatBody = this.parsing(jsonParams, "chatBody");
            String botUserKey = this.parsing(jsonParams, "botUserKey");
            division = this.parsing(jsonParams, "division");
            vop.set(botUserKey, String.valueOf(chat.getChatId()));
            vop.set("lastMes", "종료");
            if (vop.get(botUserKey).equals("0")) {
                vop.set("adminCode", String.valueOf(chatMapper.selectUserList(392).getUserCode()));
                vop.set("userCode", String.valueOf(chatMapper.selectUserList(1186).getUserCode()));
                vop.set("adminLogin", chatMapper.selectUserList(392).getUserId() + ", " + chatMapper.selectUserList(392).getUserPw());
                vop.set("userLogin", chatMapper.selectUserList(1186).getUserId() + ", " + chatMapper.selectUserList(1186).getUserPw());
                if (division.contains("0")) {
                    return this.loginSer();
                } else if (division.contains("99")) {
                    return exit.exit(jsonParams);
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
                if (division.contains("0") || chatBody.equals(vop.get("adminLogin")) || chatBody.equals(vop.get("userLogin"))) {
                    vop.set("overlap", "1");
                    return fallback.fallback(jsonParams);
                } else if (division.contains("1")) {
                    return this.menuSer(jsonParams);
                } else if (division.contains("2") || division.contains("3")) {
                    return this.classSer(jsonParams);
                } else if (division.contains("4") || division.contains("5") || division.contains("6")) {
                    return this.examSer(jsonParams);
                } else if (division.contains("7") || division.contains("8")) {
                    return this.analysisSer(jsonParams);
                } else if (division.contains("99")) {
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
            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                title = "출결관리";
                img = "https://i.pinimg.com/564x/3c/02/af/3c02afb940e4aa0e7c9448d75ee0f04f.jpg";
                url = "http://localhost:3000/teacher/attendance";
                title1 = "시험분석";
                img1 = "https://i.pinimg.com/564x/59/e0/28/59e0283e88f18b5c35ed59dbdb869b8d.jpg";
                vop.set("thirdUrl", "http://localhost:3000/teacherstreaming");
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                title = "출석체크";
                img = "https://i.pinimg.com/564x/3c/02/af/3c02afb940e4aa0e7c9448d75ee0f04f.jpg";
                url = "http://localhost:3000/studentstreaming";
                title1 = "오답노트";
                img1 = "https://i.pinimg.com/564x/3f/fb/3b/3ffb3ba83f1be537725827d331452695.jpg";
                vop.set("thirdUrl", "http://localhost:3000/studentstreaming");
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
            division = this.parsing(jsonParams, "division");
            String title2 = null;
            if (division.contains("2")) {
                title = "화상교육";
                img = "https://cdn.pixabay.com/photo/2018/10/23/10/09/video-recording-3767454_1280.jpg";
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    title1 = "출결관리";
                    title2 = "시험분석";
                    url = "http://localhost:3000/teacherstreaming";
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    title1 = "출석체크";
                    title2 = "오답노트";
                    url = "http://localhost:3000/studentstreaming";
                }
            } else if (division.contains("3")) {
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출결관리";
                    url = "http://localhost:3000/teacher/attendance";
                    title1 = "화상교육";
                    title2 = "시험분석";
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    title = "출석체크";
                    url = "http://localhost:3000/student/attendance";
                    title1 = "화상교육";
                    title2 = "오답노트";
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
            vop.set("mesKind", "webLinkUrl");
            vop.set("kind", "webLink");
            vop.set("secondMes", title1);
            vop.set("thirdMes", title2);
            this.addData(jsonParams, "b", null);
            this.addData(jsonParams, "c", null);
            this.addData(jsonParams, "b", title);
            return this.addJson("car", "qBut");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("classSer logic ERROR");
            return null;
        }
    }

    public Map<String, Object> examSer(Map<String, Object> jsonParams) {
        try {
            if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                division = this.parsing(jsonParams, "division");
                chatBody = this.parsing(jsonParams, "chatBody");
                vop.set("lastMes", "");
                //test: 기준 세우기
                vop.set("kor", "국어");
                vop.set("eng", "영어");
                vop.set("mat", "수학");
                vop.set("eco", "경제");
                vop.set("his", "한국사");
                vop.set("phl", "생활과윤리");
                vop.set("phy", "물리");
                vop.set("bio", "생명과학");
                vop.set("for", "제2외국어");
                if (division.contains("5")) {
                    logger.info("과목종류 진입");
                    vop.set("text", "과목종류를 선택해주세요.");
                    vop.set("firstMes", vop.get("eco"));
                    vop.set("secondMes", vop.get("phy"));
                    vop.set("thirdMes", vop.get("bio"));
                    vop.set("fourthMes", vop.get("for"));
                    if (chatBody.contains("고사")) {
                        vop.set("examKind", chatBody);
                    }
                    this.addData(jsonParams, "b", null);
                    this.addData(jsonParams, "c", null);
                    this.addData(jsonParams, "b", "과목종류");
                    return this.addJson("sim", "qBut");
                } else if (division.contains("6")) {
                    vop.set("subjectKind", chatBody);
                    examAnalysis.setUserCode(chat.getUserCode());
                    examAnalysis.setExamKind(examAnalysisMapper.codeExamKind(vop.get("examKind").toString()));
                    examAnalysis.setSubjectCode(examAnalysisMapper.codeSubjectKind(vop.get("subjectKind").toString()));
                    ArrayList<ExamAnalysis> analyses = examAnalysisMapper.selectList(examAnalysis);
                    for (int i = 0; i < analyses.size(); i++) {
                        examAnalysis.setExamNum(analyses.get(i).getExamNum());
                        examAnalysis.setWrongAnswer(analyses.get(i).getWrongAnswer());
                    }
                    exam.setExamNum(examAnalysis.getExamNum());
                    exam.setExamKind(examAnalysis.getExamKind());
                    exam.setSubjectCode(examAnalysis.getSubjectCode());
                    ArrayList<Exam> exams = examMapper.selectList(exam);

                    for (int i = 0; i < exams.size(); i++) {
                        exam.setExamQuestion(exams.get(i).getExamQuestion());
                        exam.setExamChoice1(exams.get(i).getExamChoice1());
                        exam.setExamChoice2(exams.get(i).getExamChoice2());
                        exam.setExamChoice3(exams.get(i).getExamChoice3());
                        exam.setExamChoice3(exams.get(i).getExamChoice3());
                        exam.setExamChoice4(exams.get(i).getExamChoice4());
                        exam.setExamAnswer(exams.get(i).getExamAnswer());
                        exam.setExamContent(exams.get(i).getExamContent());
                        if (exams.get(i).getExamChoice5() != "") {
                            exam.setExamChoice5(exams.get(i).getExamChoice5());
                        } else {
                            exam.setExamChoice5("없음");
                        }
                    }

                    if (examAnalysis.getExamNum() == 0) {
                        title = (vop.get("examKind") + " 틀린 문제가 없어요!");
                        title1 = "축하합니다. 만점이에요 :)";
                    } else {
                        title = (vop.get("examKind") + " " + vop.get("subjectKind") + " 오답노트");
                        title1 = "[" + examAnalysis.getExamNum() + "번 문제] 정답: (" + exam.getExamAnswer() + ") 오답: (" + examAnalysis.getWrongAnswer() + ")" +
                                "\n- " + exam.getExamQuestion() + "\n1. " + exam.getExamChoice1() + " 2. " + exam.getExamChoice2() + " 3. " + exam.getExamChoice3() + " 4. " + exam.getExamChoice4() + " 5. " + exam.getExamChoice5() +
                                "\n해설: " + exam.getExamContent();
                    }
                    vop.set("title", title);
                    vop.set("img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRDHlNX5vcEeSgkLeAGU9PQ8g3qDFgG7sdrSQ&usqp=CAUhttps://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRDHlNX5vcEeSgkLeAGU9PQ8g3qDFgG7sdrSQ&usqp=CAU");
                    vop.set("description", title1);

                    vop.set("firstMes", "메뉴로 이동");
                    vop.set("firstUrl", "메뉴로 이동");
                    vop.set("kind", "message");
                    vop.set("mesKind", "messageText");
                    vop.set("thirdMes", "시험 선택");
                    vop.set("secondMes", "과목 선택");
                    vop.set("lastMes", "종료");
                    this.addData(jsonParams, "b", null);
                    this.addData(jsonParams, "c", null);
                    this.addData(jsonParams, "b", "오답노트");
                    return this.addJson("car", "qBut");
                }
                vop.set("text", "시험종류를 선택해주세요.");
                vop.set("firstMes", "2학기 기말고사");
                vop.set("secondMes", "2학기 중간고사");
                vop.set("thirdMes", "1학기 기말고사");
                vop.set("fourthMes", "1학기 중간고사");
                this.addData(jsonParams, "b", null);
                this.addData(jsonParams, "c", null);
                this.addData(jsonParams, "b", "시험종류");
                return this.addJson("sim", "qBut");
            } else if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                logger.info("admin 접근 불가");
                return fallback.fallback(jsonParams);
            } else {
                logger.error("사용자구분 ERROR");
                return null;
            }
        } catch (Exception e) {
            logger.error("analysis logic ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> analysisSer(Map<String, Object> jsonParams) {
        try {
            division = this.parsing(jsonParams, "division");
            vop.set("firstMes", "");
            vop.set("firstUrl", "");
            vop.set("mesKind", "");
            vop.set("kind", "");
            vop.set("lastMes", "메뉴로 이동");
            Chat userList = chatMapper.selectUserList(392);
            String des = null;
            double allAvg = 0;
            double classAvg = 0;

            ArrayList<ExamAnalysis> analyses = examAnalysisMapper.selectAvg();
            examAnalysis.setFs(analyses.get(0).getAvgScore());
            examAnalysis.setSes(analyses.get(1).getAvgScore());
            examAnalysis.setTs(analyses.get(2).getAvgScore());
            examAnalysis.setFos(analyses.get(3).getAvgScore());
            examAnalysis.setFis(analyses.get(4).getAvgScore());
            examAnalysis.setSis(analyses.get(5).getAvgScore());
            examAnalysis.setSevs(analyses.get(6).getAvgScore());
            examAnalysis.setEs(analyses.get(7).getAvgScore());
            examAnalysis.setNs(analyses.get(8).getAvgScore());
            examAnalysis.setFms(analyses.get(9).getAvgScore());
            examAnalysis.setFfs(analyses.get(10).getAvgScore());
            examAnalysis.setSms(analyses.get(11).getAvgScore());
            examAnalysis.setSfs(analyses.get(12).getAvgScore());

            ArrayList<ExamAnalysis> homeAvg = examAnalysisMapper.comepareSvg();
            examAnalysis.setFs1(homeAvg.get(0).getAvgHome());
            examAnalysis.setSes1(homeAvg.get(1).getAvgHome());
            examAnalysis.setTs1(homeAvg.get(2).getAvgHome());
            examAnalysis.setFos1(homeAvg.get(3).getAvgHome());
            examAnalysis.setFis1(homeAvg.get(4).getAvgHome());
            examAnalysis.setSis1(homeAvg.get(5).getAvgHome());
            examAnalysis.setSevs1(homeAvg.get(6).getAvgHome());
            examAnalysis.setEs1(homeAvg.get(7).getAvgHome());
            examAnalysis.setNs1(homeAvg.get(8).getAvgHome());
            examAnalysis.setFms1(homeAvg.get(9).getAvgHome());
            examAnalysis.setFfs1(homeAvg.get(10).getAvgHome());
            examAnalysis.setSms1(homeAvg.get(11).getAvgHome());
            examAnalysis.setSfs1(homeAvg.get(12).getAvgHome());

            if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                if (division.contains("8")) {
                    if (chatBody.contains("시험")) {
                        allAvg = Math.round((examAnalysis.getFms() + examAnalysis.getFfs() + examAnalysis.getSms() + examAnalysis.getSfs()) / 4);
                        classAvg = Math.round((examAnalysis.getFms1() + examAnalysis.getFfs1() + examAnalysis.getSms1() + examAnalysis.getSfs1()) / 4);
                        title1 = "과목기준";
                        img = "https://cdn.pixabay.com/photo/2017/10/17/14/10/financial-2860753_1280.jpg";
                        des = "--- 1학년 평균 점수 " + allAvg + " ---" +
                                "\n1학기 중간고사 : " + Math.round(examAnalysis.getFms()) +
                                "\n1학기 기말고사 : " + Math.round(examAnalysis.getFfs()) +
                                "\n2학기 중간고사 : " + Math.round(examAnalysis.getSms()) +
                                "\n2학기 기말고사 : " + Math.round(examAnalysis.getSfs()) +
                                "\n--- " + userList.getCurGrade() + "학년 " + userList.getHomeClass() + "반 평균 점수 " + classAvg + " ---" +
                                "\n1학기 중간고사 : " + Math.round(examAnalysis.getFms1()) +
                                "\n1학기 기말고사 : " + Math.round(examAnalysis.getFfs1()) +
                                "\n2학기 중간고사 : " + Math.round(examAnalysis.getSms1()) +
                                "\n2학기 기말고사 : " + Math.round(examAnalysis.getSfs1());
                    } else if (chatBody.contains("과목")) {
                        allAvg = Math.round((examAnalysis.getFs() + examAnalysis.getSes() + examAnalysis.getTs() + examAnalysis.getFos() + examAnalysis.getFis() + examAnalysis.getSis() + examAnalysis.getSevs() + examAnalysis.getEs() + examAnalysis.getNs()) / 9);
                        classAvg = Math.round((examAnalysis.getFs1() + examAnalysis.getSes1() + examAnalysis.getTs1() + examAnalysis.getFos1() + examAnalysis.getFis1() + examAnalysis.getSis1() + examAnalysis.getSevs1() + examAnalysis.getEs1() + examAnalysis.getNs1()) / 9);
                        title1 = "시험기준";
                        img = "https://cdn.pixabay.com/photo/2016/10/09/08/32/digital-marketing-1725340_1280.jpg";
                        des = "--- 1학년 평균 점수 " + allAvg + " ---" +
                                "\n" + vop.get("kor") + " : " + Math.round(examAnalysis.getFs()) +
                                "\n" + vop.get("eng") + " : " + Math.round(examAnalysis.getSes()) +
                                "\n" + vop.get("mat") + " : " + Math.round(examAnalysis.getTs()) +
                                "\n" + vop.get("phl") + " : " + Math.round(examAnalysis.getFos()) +
                                "\n" + vop.get("eco") + " : " + Math.round(examAnalysis.getFis()) +
                                "\n" + vop.get("phy") + " : " + Math.round(examAnalysis.getSis()) +
                                "\n" + vop.get("bio") + " : " + Math.round(examAnalysis.getSevs()) +
                                "\n" + vop.get("his") + " : " + Math.round(examAnalysis.getEs()) +
                                "\n" + vop.get("for") + " : " + Math.round(examAnalysis.getNs()) +
                                "\n--- " + userList.getCurGrade() + "학년 " + userList.getHomeClass() + "반 평균 점수 " + classAvg + " ---" +
                                "\n" + vop.get("kor") + " : " + Math.round(examAnalysis.getFs1()) +
                                "\n" + vop.get("eng") + " : " + Math.round(examAnalysis.getSes1()) +
                                "\n" + vop.get("mat") + " : " + Math.round(examAnalysis.getTs1()) +
                                "\n" + vop.get("phl") + " : " + Math.round(examAnalysis.getFos1()) +
                                "\n" + vop.get("eco") + " : " + Math.round(examAnalysis.getFis1()) +
                                "\n" + vop.get("phy") + " : " + Math.round(examAnalysis.getSis1()) +
                                "\n" + vop.get("bio") + " : " + Math.round(examAnalysis.getSevs1()) +
                                "\n" + vop.get("his") + " : " + Math.round(examAnalysis.getEs1()) +
                                "\n" + vop.get("for") + " : " + Math.round(examAnalysis.getNs1());
                    }
                    vop.set("firstMes", "자세히 확인");
                    vop.set("firstUrl", "http://localhost:3000/teacher/grade/grade");
                    vop.set("mesKind", "webLinkUrl");
                    vop.set("kind", "webLink");

                    vop.set("description", des);
                    vop.set("title", "[" + (userList.getCurGrade() + "학년 " + userList.getHomeClass() + "반] 의 " + "분석 결과"));
                    vop.set("img", img);
                    vop.set("secondMes", "종료");
                    vop.set("thirdMes", title1);
                    this.addData(jsonParams, "b", null);
                    this.addData(jsonParams, "c", null);
                    this.addData(jsonParams, "b", "시험분석");
                    return this.addJson("car", "qBut");
                }
                vop.set("text", "분석할 기준을 고르세요.");
                vop.set("secondMes", "시험기준");
                vop.set("thirdMes", "과목기준");
                vop.set("kind", "message");
                vop.set("mesKind", "messageText");
                this.addData(jsonParams, "b", null);
                this.addData(jsonParams, "c", null);
                this.addData(jsonParams, "b", "분석기준");
                return this.addJson("sim", "qBut");
            } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                logger.info("user 접근 불가");
                return fallback.fallback(jsonParams);
            } else {
                logger.error("사용자구분 ERROR");
                return null;
            }
        } catch (Exception e) {
            logger.error("analysis logic ERROR");
            e.printStackTrace();
            return null;
        }
    }
}