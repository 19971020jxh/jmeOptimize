package org.jxh.project.jmeoptimize;

import Characteristic_Curve.Class1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class JmeoptimizeApplication {

    public static void main(String[] args) throws  Exception{
        System.setProperty("java.awt.headless", "false");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(JmeoptimizeApplication.class);
        builder.headless(false).run();



      // SpringApplication.run(JmeoptimizeApplication.class, args);
    }


}
