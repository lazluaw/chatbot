package com.chatbot.web.proxy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Lazy
@Component
public class Inventory<Data> {
    //add, get(one), get(list), clear, size, newInventory
    private ArrayList<Data> inventory;
    public Inventory() {
        inventory = new ArrayList<>();
    }
    public void add(Data data) {
        inventory.add(data);
    }
    public Data get(int number) {
        Function<Integer, Data> function = data -> get(data);
        return function.apply(number);
    }
    public ArrayList<Data> get() {
        return inventory;
    }
    public void size() {
        inventory.size();
    }
    public void clear() {
        inventory.clear();
    }
    public void newInventory() {
        inventory = new ArrayList<Data>();
    }
}
