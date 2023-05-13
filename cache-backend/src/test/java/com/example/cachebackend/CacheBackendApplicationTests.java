package com.example.cachebackend;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheBackendApplicationTests {

    @Test
    void contextLoads() {
        String s = RandomUtil.randomNumbers(6);
        System.out.println(s);
    }

}
