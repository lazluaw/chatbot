package com.chatbot.web.conversions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class Serializer {
    private JSONObject obj, arrObj, innerObj, mostInnerObj, mostInnerObj1,
            innerObj1, innerObj2, innerObj3, innerObj4, innerObj5, innerObj6, innerObj7, innerObj8, innerObj9;
    private JSONArray arr, arr1;

    public Map<String, Object> simpleTextSer() throws JsonProcessingException {
        obj = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        innerObj = new JSONObject();
        mostInnerObj = new JSONObject();

        mostInnerObj.put("text", "기본 텍스트");
        innerObj.put("simpleText", mostInnerObj);
        arr.add(innerObj);
        arrObj.put("outputs", arr);
        obj.put("template", arrObj);
        obj.put("version", "2.0");

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        System.out.println(jsonStr);
        return obj;
    }

    public Map<String, Object> subjectCodeSer() throws JsonProcessingException {
        obj = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        innerObj = new JSONObject();
        mostInnerObj = new JSONObject();

        arr1 = new JSONArray();
        arrObj = new JSONObject();
        innerObj1 = new JSONObject();
        innerObj2 = new JSONObject();
        innerObj3 = new JSONObject();
        innerObj4 = new JSONObject();
        innerObj5 = new JSONObject();
        innerObj6 = new JSONObject();
        innerObj7 = new JSONObject();
        innerObj8 = new JSONObject();
        innerObj9 = new JSONObject();
        mostInnerObj1 = new JSONObject();

        innerObj1.put("type", "text");
        innerObj1.put("label", "이미지카드");
        innerObj1.put("message", "이미지카드");
        arr1.add(innerObj1);

        mostInnerObj1.put("blockId", "59ed51909418c2298c0e0bcd");
        innerObj2.put("data", mostInnerObj1);
        innerObj2.put("type", "block");
        innerObj2.put("label", "처음으로");
        innerObj2.put("message", "처음으로");
        arr1.add(innerObj2);

        innerObj3.put("type", "text");
        innerObj3.put("label", "국어");
        innerObj3.put("message", "국어");
        arr1.add(innerObj3);

        innerObj4.put("type", "text");
        innerObj4.put("label", "국어");
        innerObj4.put("message", "국어");
        arr1.add(innerObj4);

        innerObj5.put("type", "text");
        innerObj5.put("label", "국어");
        innerObj5.put("message", "국어");
        arr1.add(innerObj5);

        innerObj6.put("type", "text");
        innerObj6.put("label", "국어");
        innerObj6.put("message", "국어");
        arr1.add(innerObj6);

        innerObj7.put("type", "text");
        innerObj7.put("label", "국어");
        innerObj7.put("message", "국어");
        arr1.add(innerObj7);

        innerObj8.put("type", "text");
        innerObj8.put("label", "국어");
        innerObj8.put("message", "국어");
        arr1.add(innerObj8);

        innerObj9.put("type", "text");
        innerObj9.put("label", "국어");
        innerObj9.put("message", "국어");
        arr1.add(innerObj9);

        obj.put("quickReplies", arr1);

        mostInnerObj.put("text", "과목명을 선택해주세요.");
        innerObj.put("simpleText", mostInnerObj);
        arr.add(innerObj);
        arrObj.put("outputs", arr);
        obj.put("template", arrObj);
        obj.put("version", "2.0");

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        System.out.println(jsonStr);
        return obj;
    }

    public Map<String, Object> examAnalysisSer() throws JsonProcessingException {
        obj = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        innerObj = new JSONObject();
        mostInnerObj = new JSONObject();

        mostInnerObj.put("text", "오답노트 결과");
        innerObj.put("simpleText", mostInnerObj);
        arr.add(innerObj);
        arrObj.put("outputs", arr);
        obj.put("template", arrObj);
        obj.put("version", "2.0");

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        System.out.println(jsonStr);
        return obj;
    }
}