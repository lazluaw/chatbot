package com.chatbot.web.proxy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.function.Function;

@Lazy
@Component
public class Box<Data> {
    private HashMap<String, Data> box;
    public Box() {
        box = new HashMap<String, Data>();
    }
    //put, get(key), get(map), size, clear, newBox
    public void put(String string, Data data) {
        box.put(string, data);
    }
    public Data get(String string) {
        Function<String, Data> function = data -> box.get(data);
        return function.apply(string);
    }
    public HashMap<String, Data> get() {
        return box;
    }
    public void size(Data data) {
        box.size();
    }
    public void clear() {
        box.clear();
    }
    public void newBox() {
        box = new HashMap<String, Data>();
    }
}