package org.jxh.project.jmeoptimize;

import Characteristic_Curve.Class1;
import Data_Processing.Data_Process;

import Data_Processing_LiuLiang.Data_Process_LiuLiang;
import chulijisuan.Chulijisuan;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest()
class JmeoptimizeApplicationTests {

    @Test
    void contextLoads() throws  Exception{
       // Chulijisuan chulijisuan=new Chulijisuan();
       // chulijisuan.dispose();
      //  Chulijisuan.disposeAllInstances();

//        System.setProperty("java.awt.headless", "false");
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(JmeoptimizeApplicationTests.class);
//        builder.headless(false).run();
//        Class1 class1=new Class1();
//        class1.Characteristic_Curve();

        Data_Process dp =new Data_Process();
         dp.Data_Processing("F:\\Desktop\\1-2号机组特征曲线数据.xls");
     //   Arrays.stream(dp.Data_Processing("C:\\Users\\31817\\Desktop\\11.xlsx")).forEach(System.out::println);

       // Data_Process_LiuLiang data_process_liuLiang=new Data_Process_LiuLiang();
      //  data_process_liuLiang.Data_Processing_LiuLiang("F:\\Desktop\\1-2号机组特征曲线数据.xls");

    }

}
