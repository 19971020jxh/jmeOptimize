package org.jxh.project.jmeoptimize.api;

import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.trainDataDao;
import org.jxh.project.jmeoptimize.pojo.trainData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class trainDataController {
    @Autowired private trainDataDao trainDataDao;

    @GetMapping("/getjiQis")
    JSONObject getJiQis(){
        JSONObject rs=new JSONObject();
        rs.put("jiQis",trainDataDao.jiQis());
        return  rs;
    }
    @GetMapping("/getData")
    JSONObject getData(@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();
        rs.put("data",
                trainDataDao.getData(jiqi).stream().collect(Collectors.groupingBy(trainData::getXiaolv))
        );
        return  rs;
    }

    @GetMapping("/getData_shuJuWeiHu")
    JSONObject getData_shuJuWeiHu(@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();
        rs.put("data",trainDataDao.getData_shuJuWeiHu(jiqi));
        return  rs;
    }

    @GetMapping("/addData")
    JSONObject addData(@RequestParam("shuitou")Integer shuitou,@RequestParam("chuli")Integer chuli,@RequestParam("liuLiang")Integer liuLiang){
    JSONObject rs=new JSONObject();
    trainDataDao.addData(shuitou,chuli,liuLiang);
    return  rs;
    }
    @GetMapping("/getUpdate")
    JSONObject getUpdate(@RequestParam("id")Integer id,@RequestParam("shuitou")Integer shuitou,@RequestParam("chuli")Integer chuli,@RequestParam("liuLiang")Integer liuLiang){
        JSONObject rs=new JSONObject();
        rs.put("old",trainDataDao.updateSELECT(id));
        trainDataDao.getUpdate(id,shuitou,chuli,liuLiang);
        return  rs;
    }
    @GetMapping("/getDelete")
    JSONObject getDelete(@RequestParam("id")Integer id){
        JSONObject rs=new JSONObject();
        trainDataDao.getDelete(id);
        return  rs;
    }

}
