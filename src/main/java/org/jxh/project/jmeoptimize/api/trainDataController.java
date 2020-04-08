package org.jxh.project.jmeoptimize.api;

import Characteristic_Curve.Class1;
import Data_Processing.Data_Process;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.trainDataDao;
import org.jxh.project.jmeoptimize.pojo.moXingUtil;
import org.jxh.project.jmeoptimize.pojo.trainData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class trainDataController {
    @Autowired private trainDataDao trainDataDao;


    @GetMapping("/getData")
    JSONObject getData(@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();
        if(jiqi==1){
            rs.put("data",
                    trainDataDao.getData_1().stream().collect(Collectors.groupingBy(trainData::getXiaolv))
            );
        }
        if(jiqi==2){
            rs.put("data",
                    trainDataDao.getData_2().stream().collect(Collectors.groupingBy(trainData::getXiaolv))
            );
        }
        return  rs;
    }
    @GetMapping("curve")
    void Curve() throws  Exception{
        // -> 特征曲线窗口 4/8
        Class1 class1=new Class1();
        class1.Characteristic_Curve();
    }

    @GetMapping("/getData_shuJuWeiHu")
    JSONObject getData_shuJuWeiHu(@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();
        rs.put("data",trainDataDao.getData_shuJuWeiHu(jiqi));
        return  rs;
    }

//    @GetMapping("/addData")
//    JSONObject addData(@RequestParam(value = "shuitou",required = false)String shuitou,@RequestParam(value = "chuli",required = false)String chuli,@RequestParam(value = "liuLiang",required = false)String liuLiang,@RequestParam("jiqi")int jiqi){
//    JSONObject rs=new JSONObject();
////        Random random=new Random();
//////        random.setSeed(System.currentTimeMillis());
//    trainDataDao.addData(Double.parseDouble(shuitou.trim()),Double.parseDouble(chuli.trim()),Double.parseDouble(liuLiang),jiqi,null,null);
//    return  rs;
//    }
//    @GetMapping("/getUpdate")
//    JSONObject getUpdate(@RequestParam("id")Integer id,@RequestParam("jiqi")Integer jiqi, @RequestParam(value = "shuitou",required = false)String shuitou,@RequestParam(value = "chuli",required = false)String chuli,@RequestParam(value = "liuLiang",required = false)String liuLiang){
//        JSONObject rs=new JSONObject();
//        trainData traindata=null;
//        if(jiqi==1){
//            traindata=  trainDataDao.updateSELECT_1(id) ;
//        }
//        if(jiqi==2){
//            traindata=  trainDataDao.updateSELECT_2(id) ;
//        }
//        rs.put("old",traindata);
//        trainDataDao.getUpdate(id,jiqi,Double.parseDouble(shuitou.trim()),Double.parseDouble(chuli.trim()),Double.parseDouble(liuLiang));
//        return  rs;
//    }

//    @GetMapping("/getDelete")
//    JSONObject getDelete(@RequestParam("id")Integer id,@RequestParam("jiqi")int jiqi){
//        JSONObject rs=new JSONObject();
//        trainDataDao.getDelete(id,jiqi);
//        return  rs;
//    }

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


//    @PostMapping("/newJiQi")
//    JSONObject newJiQi(@RequestBody List<JSONObject> trainDatas){
//        JSONObject rs=new JSONObject();
//        Random random=new Random();
//        trainDatas.stream().forEach(e->{
//            random.setSeed(System.currentTimeMillis());
//            trainDataDao.addData(e.getDouble("shuitou"),e.getDouble("chuli"),e.getDouble("liuLiang"),e.getIntValue("jiqi"),random.nextFloat(),null);
//        });
//        return rs;
//    }

    /**
     * 保存修改!!!!
     * @param json
     * @return
     */
    @PostMapping("/keep_1")
    JSONObject keep_1(@RequestBody String json){
        JSONObject rs=new JSONObject();
        trainDataDao.delete(1);
       List<trainData> list= JSONObject.parseArray(JSON.parseObject(json).getString("list"),trainData.class);
       list.forEach(e->{
            trainDataDao.addData(e.getShuitou(),e.getChuli(),e.getLiuliang(),1,null,e.getXiaolv());
        });
        moXingUtil.data_process(list,1);
        return  rs;
    }
    @PostMapping("/keep_2")
    JSONObject keep_2(@RequestBody String json){
        JSONObject rs=new JSONObject();
        trainDataDao.delete(2);
        List<trainData> list= JSONObject.parseArray(json,trainData.class);
        list.forEach(e->{
            trainDataDao.addData(e.getShuitou(),e.getChuli(),e.getLiuliang(),2,null,e.getXiaolv());
        });
        moXingUtil.data_process(list,2);
        return  rs;
    }

    @PostMapping("/deleteAll")
    JSONObject deleteAll(){
        JSONObject rs=new JSONObject();
        trainDataDao.deleteAll();
        return  rs;
    }


    @PostMapping("addBatch")
    @ResponseBody
    public JSONObject addBatch(@RequestParam("file")MultipartFile file,@RequestParam("jiqi")int jiqi) throws  Exception{
        JSONObject rs=new JSONObject();
        ExcelListener listener= new ExcelListener();
        ExcelReader reader= new ExcelReader(new BufferedInputStream(file.getInputStream()),null,listener,false);
        reader.read(new Sheet(1,1,trainData.class));
        trainDataDao.delete(jiqi);
        List<trainData> list= listener.getDatas();
        list.forEach(e->{
            trainDataDao.addData(e.getShuitou(),e.getChuli(),e.getLiuliang(),jiqi,null,e.getXiaolv());
        });
       // rs.put("list",list);



        return  rs;
    }

    @PostMapping("data_process")
    public JSONObject data_process(@RequestParam("jiqi")int jiqi){
        moXingUtil.data_process( trainDataDao.getDataAll(jiqi),jiqi);
        return  null;
    }

    @PostMapping("niHe")
    public JSONObject niHe(@RequestBody String form) throws  Exception{
        moXingUtil.niHe(JSONObject.parseObject(form));
        return  null;
    }
    @PostMapping("SaveModel")
    public JSONObject SaveModel(@RequestParam("jiqi")int jiqi) throws  Exception{
            moXingUtil.SaveModel(jiqi);
        return  null;
    }


    @GetMapping("/excels")
    public void excels(HttpServletRequest request, HttpServletResponse response,@RequestParam("jiqi")Integer jiqi) throws  Exception{
     List<trainData> list=trainDataDao.excels(jiqi);
     response.setContentType("application/vnd.ms-excel");
     response.setCharacterEncoding("utf-8");
     response.setHeader("Content-Disposition","attachment;filename=机器"+ URLEncoder.encode("机器"+jiqi+"数据", "UTF-8") +".xlsx");
     EasyExcel.write(response.getOutputStream(),trainData.class).sheet("sheet1").doWrite(list);
    }

  class   ExcelListener extends  AnalysisEventListener<trainData>{
      private List<trainData> datas = new ArrayList<>();
      @Override
      public void invoke(trainData o, AnalysisContext analysisContext) {
          datas.add(o);
      }
      @Override
      public void doAfterAllAnalysed(AnalysisContext analysisContext) {}
      public List<trainData> getDatas() {
          return datas;
      }

      public void setDatas(List<trainData> datas) {
          this.datas = datas;
      }
  }
}
