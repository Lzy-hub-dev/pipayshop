package com.example.pipayshopapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

@SpringBootTest
class PipayshopApiApplicationTests {

    @Test
    void contextLoads() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("12");
        strings.add("13");
        strings.add("14");
        String s = JSON.toJSONString(strings);
        System.out.println(s);
        List<String> collect = JSON.parseArray(s, String.class);

    }

}
