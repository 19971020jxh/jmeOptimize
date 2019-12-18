package org.jxh.project.jmeoptimize.api;

import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.trainDataDao;
import org.jxh.project.jmeoptimize.pojo.trainData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    JSONObject addData(@RequestParam(value = "shuitou",required = false)String shuitou,@RequestParam(value = "chuli",required = false)String chuli,@RequestParam(value = "liuLiang",required = false)String liuLiang,@RequestParam("jiqi")int jiqi){
    JSONObject rs=new JSONObject();
        Random random=new Random();
        random.setSeed(System.currentTimeMillis());
    trainDataDao.addData(Double.parseDouble(shuitou.trim()),Double.parseDouble(chuli.trim()),Double.parseDouble(liuLiang),jiqi,random.nextFloat());
    return  rs;
    }
    @GetMapping("/getUpdate")
    JSONObject getUpdate(@RequestParam("id")Integer id,@RequestParam(value = "shuitou",required = false)String shuitou,@RequestParam(value = "chuli",required = false)String chuli,@RequestParam(value = "liuLiang",required = false)String liuLiang){
        JSONObject rs=new JSONObject();
        rs.put("old",trainDataDao.updateSELECT(id));
        trainDataDao.getUpdate(id,Double.parseDouble(shuitou.trim()),Double.parseDouble(chuli.trim()),Double.parseDouble(liuLiang));
        return  rs;
    }
    @GetMapping("/getDelete")
    JSONObject getDelete(@RequestParam("id")Integer id){
        JSONObject rs=new JSONObject();
        trainDataDao.getDelete(id);
        return  rs;
    }

    @GetMapping("/isOut")
    JSONObject isOut(@RequestParam("shuitou")String shuitou,@RequestParam("liuLiang")String liuLiang){
        JSONObject rs=new JSONObject();
        JSONObject isOut= trainDataDao.isOut(Double.parseDouble(shuitou.trim()),Double.parseDouble(liuLiang.trim()));
        rs.put("isOut",isOut);
        return  rs;
    }


    @PostMapping("moXing/file")
    JSONObject moXingFile(@RequestParam("file")MultipartFile file){
        JSONObject rs=new JSONObject();
        System.out.println(file.getName());
        return  rs;
    }


    @PostMapping("/newJiQi")
    JSONObject newJiQi(@RequestBody List<JSONObject> trainDatas){
        JSONObject rs=new JSONObject();
        Random random=new Random();
        trainDatas.stream().forEach(e->{
            random.setSeed(System.currentTimeMillis());
            trainDataDao.addData(e.getDouble("shuitou"),e.getDouble("chuli"),e.getDouble("liuLiang"),e.getIntValue("jiqi"),random.nextFloat());
        });
        return rs;
    }

    @PostMapping("/deleteAll")
    JSONObject deleteAll(){
        JSONObject rs=new JSONObject();
        trainDataDao.deleteAll();
        return  rs;
    }

}
