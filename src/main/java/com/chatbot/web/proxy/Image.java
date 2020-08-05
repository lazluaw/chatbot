package com.chatbot.web.proxy;

import org.springframework.data.redis.core.ValueOperations;

public class Image {
    private String entry, fallback, exit, stream, attendance,
            adminM, adminAn,
            userM, userAn;
    private ValueOperations<String, Object> imgVop;
    public ValueOperations<String, Object> img() {
        imgVop.set(entry, "https://i.pinimg.com/564x/f4/62/c9/f462c9fc876243221c6ee36c3fb97b32.jpg");
        imgVop.set(fallback, "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg");
        imgVop.set(exit, "https://i.pinimg.com/564x/37/a2/6b/37a26b5b2b879c5280cbe4457d0d4649.jpg");
        imgVop.set(stream, "https://i.pinimg.com/564x/df/9b/65/df9b65458c037b6f9eac74e9035c62ad.jpg");
        imgVop.set(attendance, "https://i.pinimg.com/564x/82/e5/db/82e5dbfb38770d47232532c5c5e3c932.jpg");
        
        imgVop.set(adminM, "https://i.pinimg.com/564x/c3/d1/42/c3d1428151d94f028e3c9d1e5d86ea8f.jpg");
        imgVop.set(adminAn, "https://i.pinimg.com/564x/e7/c3/cc/e7c3cc68c94b5828e347c0e35255425d.jpg");

        imgVop.set(userM, "https://i.pinimg.com/564x/cc/8e/08/cc8e083f2c13532a94038138a6713ec1.jpg");
        imgVop.set(userAn, "https://i.pinimg.com/564x/7e/0a/70/7e0a70b7dc579def017a4cd56ad826f9.jpg");
        
        return imgVop;
    }
}
