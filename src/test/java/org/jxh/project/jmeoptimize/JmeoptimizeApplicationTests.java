package org.jxh.project.jmeoptimize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.stream.IntStream;

//@SpringBootTest
class JmeoptimizeApplicationTests {

    @Test
    void contextLoads() {

        System.out.println(DigestUtils.md5DigestAsHex("管理员".getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex("用户".getBytes()));
    }

}
