package com.chatbot.web.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class BicycleSerializer {
    private String json;
    private JSONObject inner, outer, middleInner, finalInner;
    private JSONArray list;
    public BicycleSerializer() {
        inner = new JSONObject();
        outer = new JSONObject();
        list = new JSONArray();
        middleInner = new JSONObject();
        finalInner = new JSONObject();
    }
    public String addReservation() {
        inner.put("text", "예약이 필요합니다. 원하는 날짜를 작성해주세요 :)");
        outer.put("simpleText", inner);
        list.add(outer);
        middleInner.put("outputs", list);

        finalInner.put("template", middleInner);
        finalInner.put("version", "2.0");

        json = finalInner.toJSONString();
        inner.clear();
        outer.clear();
        list.clear();
        middleInner.clear();
        finalInner.clear();
        return json;
    }
    public String checkReservation() {

        inner.put("text", "예약일을 반드시 체크해주시기 바랍니다. 맞으신가요?");
        outer.put("simpleText", inner);
        list.add(outer);
        middleInner.put("outputs", list);

        finalInner.put("template", middleInner);
        finalInner.put("version", "2.0");

        json = finalInner.toJSONString();
        inner.clear();
        outer.clear();
        list.clear();
        middleInner.clear();
        finalInner.clear();
        return json;
    }
    public String completeReservation() {
        inner.put("text", "예약되었습니다. 감사합니다.");
        outer.put("simpleText", inner);
        list.add(outer);
        middleInner.put("outputs", list);

        finalInner.put("template", middleInner);
        finalInner.put("version", "2.0");

        json = finalInner.toJSONString();
        inner.clear();
        outer.clear();
        list.clear();
        middleInner.clear();
        finalInner.clear();
        return json;
    }
}