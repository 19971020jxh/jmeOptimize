package org.jxh.project.jmeoptimize.api;

import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.trainDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class trainDataController {
    @Autowired private trainDataDao trainDataDao;

    @RequestMapping("/getTrainData")
    JSONObject getTrainData(){
        return null;
    }
}
