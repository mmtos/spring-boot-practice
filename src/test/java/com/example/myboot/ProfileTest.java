package com.example.myboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public class ProfileTest {
    @Autowired
    Environment env;

    @Test
    void checkLocalEnvironment() {
        for(String profile : env.getActiveProfiles()) {
            System.out.println(profile);
        }
        System.out.println(env.getProperty("test"));
        System.out.println(env.getProperty("test1"));
        System.out.println(env.getProperty("test2"));
        System.out.println(env.getProperty("commontest"));

    }
}
