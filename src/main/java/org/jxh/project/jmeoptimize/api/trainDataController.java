package org.jxh.project.jmeoptimize.api;

import Characteristic_Curve.Class1;
import Data_Processing.Data_Process;
import chulijisuan.Chulijisuan;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.trainDataDao;
import org.jxh.project.jmeoptimize.pojo.dataListener;
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
import java.util.stream.Stream;

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
    @GetMapping("chuLi")
    void Chuli(@RequestParam("jiqi")int jiqi) throws  Exception{
        Chulijisuan chulijisuan=new Chulijisuan();
        // Could not create MWArray from unsupported MATLAB type MCOS
        chulijisuan.chulijisuan(1);
    }

    @GetMapping("/getData_shuJuWeiHu")
    JSONObject getData_shuJuWeiHu(@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();
       // dataListener listener= new dataListener();
      //  EasyExcel.read(System.getProperty("user.dir")+"Data_Process/机器"+jiqi+".xls",trainData.class,listener).sheet().doRead();
        rs.put("data",trainDataDao.getData_shuJuWeiHu(beforeJiQi(jiqi)));
        return  rs;
    }

    @GetMapping("/addData")
    JSONObject addData(@RequestParam("xiaolv")int xiaolv, @RequestParam(value = "shuitou",required = false)String shuitou,@RequestParam(value = "chuli",required = false)String chuli,@RequestParam(value = "liuLiang",required = false)String liuLiang,@RequestParam("jiqi")int jiqi){
    JSONObject rs=new JSONObject();
    trainDataDao.addData(Double.parseDouble(shuitou.trim()),Double.parseDouble(chuli.trim()),Double.parseDouble(liuLiang),beforeJiQi(jiqi),null,xiaolv);
    return  rs;
    }
    @GetMapping("/getUpdate")
    JSONObject getUpdate(@RequestParam("id")Integer id,@RequestParam("jiqi")Integer jiqi,@RequestParam("xiaolv")int xiaolv, @RequestParam(value = "shuitou",required = false)String shuitou,@RequestParam(value = "chuli",required = false)String chuli,@RequestParam(value = "liuLiang",required = false)String liuLiang){
        JSONObject rs=new JSONObject();
        trainData traindata=null;
        if(jiqi==1){
            traindata=  trainDataDao.updateSELECT_1(id) ;
        }
        if(jiqi==2){
            traindata=  trainDataDao.updateSELECT_2(id) ;
        }
        rs.put("old",traindata);
        trainDataDao.getUpdate(id,beforeJiQi(jiqi),xiaolv,Double.parseDouble(shuitou.trim()),Double.parseDouble(chuli.trim()),Double.parseDouble(liuLiang));
        return  rs;
    }

    @GetMapping("/getDelete")
    JSONObject getDelete(@RequestParam("id")Integer id,@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();

        trainDataDao.getDelete(id,beforeJiQi(jiqi));
        return  rs;
    }
    @GetMapping("daoChu") JSONObject daoChu(@RequestParam("jiqi")int jiqi){
        JSONObject rs=new JSONObject();
        // 导出到这个文件中
        String path=System.getProperty("user.dir")+"/JZ_From_Databases/NO"+beforeJiQi(jiqi).toUpperCase()+"_Characteristic_curve_data.xls";//+
        // 删除原来的excel
        new File(path).delete();
        // 生成excel
        EasyExcel.write(path,trainData.class).sheet("sheet1").doWrite(trainDataDao.getData_shuJuWeiHu(beforeJiQi(jiqi)));
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

//    /**
//     * 保存修改!!!!
//     * @param json
//     * @return
//     */
//    @PostMapping("/keep_1")
//    JSONObject keep_1(@RequestBody String json){
//        JSONObject rs=new JSONObject();
//        //trainDataDao.delete(1);
//        List<trainData> list= JSONObject.parseArray(JSON.parseObject(json).getString("list"),trainData.class);
//      // list.forEach(e->{
//      //      trainDataDao.addData(e.getShuitou(),e.getChuli(),e.getLiuliang(),1,null,e.getXiaolv());
//     //   });
//        moXingUtil.data_process(list,beforeJiQi(jiqi),"all");
//        return  rs;
//    }
//    @PostMapping("/keep_2")
//    JSONObject keep_2(@RequestBody String json){
//        JSONObject rs=new JSONObject();
//      //  trainDataDao.delete(2);
//        List<trainData> list= JSONObject.parseArray(json,trainData.class);
//      //  list.forEach(e->{
//      //      trainDataDao.addData(e.getShuitou(),e.getChuli(),e.getLiuliang(),2,null,e.getXiaolv());
//     //   });
//        moXingUtil.data_process(list,2,"all");
//        return  rs;
//    }
//    @PostMapping("/keep_31")
//    JSONObject keep_31(@RequestBody String json){
//        // -> 3号机组大齿轮
//        return  null;
//    }
//    @PostMapping("/keep_32")
//    JSONObject keep_32(@RequestBody String json){
//        // -> 3号机组小齿轮
//        return  null;
//    }

    @PostMapping("/deleteAll")
    JSONObject deleteAll(){
        JSONObject rs=new JSONObject();
        trainDataDao.deleteAll();
        return  rs;
    }

    // -> excel 在JZ_Digitized_data 生成excel
    @PostMapping("uploadExcel")
    @ResponseBody
    public JSONObject uploadExcel(@RequestParam("file")MultipartFile file,@RequestParam("jiqi")int jiqi) throws  Exception{
        JSONObject rs=new JSONObject();
        dataListener listener=new dataListener();
        EasyExcel.read(new BufferedInputStream(file.getInputStream()),trainData.class,listener).sheet().doRead();
      //  ExcelListener listener= new ExcelListener();
      //  ExcelReader reader= new ExcelReader(new BufferedInputStream(file.getInputStream()),null,listener,false);
     //   reader.read(new Sheet(1,1,trainData.class));
     //   trainDataDao.delete(jiqi);
        // -> 获取批量上传的新数据
        List<trainData> list= listener.getDatas();
        // -> 清空相应表
        trainDataDao.delete(beforeJiQi(jiqi));
        // ->写进数据库
        trainDataDao.addBatch(list,beforeJiQi(jiqi));
        // -> 生成文件. 清空原来数据
        new File(System.getProperty("user.dir") + "/JZ_Digitized_data/NO" + beforeJiQi(jiqi).toUpperCase() + "_Characteristic_curve_data.xls").delete();
        EasyExcel.write(System.getProperty("user.dir") + "/JZ_Digitized_data/NO" + beforeJiQi(jiqi).toUpperCase() + "_Characteristic_curve_data.xls", trainData.class).sheet("sheet1").doWrite(list);
       // moXingUtil.data_process(Stream.of(list,list).flatMap(Collection::stream).distinct().collect(Collectors.toList()),jiqi,"all");
       // rs.put("list",list);
        return  rs;
    }

    @PostMapping("data_process")
    public JSONObject data_process(@RequestParam("jiqi")int jiqi,@RequestParam("pageName")String pageName,@RequestParam("file")MultipartFile file) throws  Exception{
        moXingUtil.data_process( beforeJiQi(jiqi).toUpperCase(),pageName,file.getInputStream());
        return  null;
    }

    @PostMapping("niHe")
    public JSONObject niHe(@RequestBody String form) throws  Exception{
        moXingUtil.niHe(JSONObject.parseObject(form));
        return  null;
    }
    @PostMapping("SaveModel")
    public JSONObject SaveModel(@RequestParam("jiqi")int jiqi,@RequestParam("pageName")String pageName) throws  Exception{
            moXingUtil.SaveModel(beforeJiQi(jiqi),pageName);
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

//  class   ExcelListener extends  AnalysisEventListener<trainData>{
//      private List<trainData> datas = new ArrayList<>();
//      @Override
//      public void invoke(trainData o, AnalysisContext analysisContext) {
//          datas.add(o);
//      }
//      @Override
//      public void doAfterAllAnalysed(AnalysisContext analysisContext) {}
//      public List<trainData> getDatas() {
//          return datas;
//      }
//
//      public void setDatas(List<trainData> datas) {
//          this.datas = datas;
//      }
//  }

    @RequestMapping("/moXingXunLianInit")
    JSONObject moXingXunLianInit(@RequestParam("pageName")String pageName){
        JSONObject rs=new JSONObject();
        rs.put("filePath",System.getProperty("user.dir")+"/JZ_From_Databases");
        return  rs;
    }


    String beforeJiQi(int jiqi){
        if(jiqi==1 || jiqi==2) return jiqi+"";
        if(jiqi==31) return  "3b";
        if(jiqi==32) return "3s";
        return null;
    }
}
