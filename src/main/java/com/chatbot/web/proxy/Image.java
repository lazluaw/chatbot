package com.chatbot.web.proxy;

import org.springframework.data.redis.core.ValueOperations;

public class Image {
    private String entry, fallback, exit, stream,
            adminM, adminAn, adminAt,
            userM, userAn, userAt;
    private ValueOperations<String, Object> imgVop;
    public ValueOperations<String, Object> img() {
        imgVop.set(entry, "https://i.pinimg.com/564x/f4/62/c9/f462c9fc876243221c6ee36c3fb97b32.jpg");
        imgVop.set(fallback, "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg");
        imgVop.set(exit, "https://i.pinimg.com/564x/37/a2/6b/37a26b5b2b879c5280cbe4457d0d4649.jpg");

        imgVop.set(adminM, "https://i.pinimg.com/564x/c3/d1/42/c3d1428151d94f028e3c9d1e5d86ea8f.jpg");
        imgVop.set(adminAn, "https://i.pinimg.com/564x/ae/68/4c/ae684c1987f27e7e365727d18811202d.jpg");

        imgVop.set(userM, "https://i.pinimg.com/564x/cc/8e/08/cc8e083f2c13532a94038138a6713ec1.jpg");
        imgVop.set(userAn, "https://i.pinimg.com/564x/7e/0a/70/7e0a70b7dc579def017a4cd56ad826f9.jpg");
        
        return imgVop;
    }
}
