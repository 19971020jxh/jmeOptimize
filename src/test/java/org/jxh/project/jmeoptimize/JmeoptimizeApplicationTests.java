package org.jxh.project.jmeoptimize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

//@SpringBootTest
class JmeoptimizeApplicationTests {

    @Test
    void contextLoads() {
        Random random=new Random();
        random.setSeed(System.currentTimeMillis());
        System.out.println(random.nextFloat());

    }

}
