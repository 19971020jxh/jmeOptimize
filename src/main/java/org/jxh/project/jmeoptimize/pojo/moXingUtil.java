package org.jxh.project.jmeoptimize.pojo;

import Data_Fitting.Class1;
import Data_Fitting.Data_FittingMCRFactory;
import Data_Fitting_LiuLiang.Data_Fit_LiuLiang;
import Data_Processing.Data_Process;
import Data_Processing_LiuLiang.Data_Process_LiuLiang;
import Save_Model_2.Save_Model_2MCRFactory;
import Save_Model_LiuLiang1.Save_LiuLiang_Model_1;
import Save_Model_LiuLiang2.Save_LiuLiang_Model_2;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.List;

/**
 * 当特征数据发生改变时,重新训练模型!
 */
public   class  moXingUtil {
    static String  path=System.getProperty("user.dir");

    /**
     *
     * @param list 修改后的数据
     * @param jiqi
     */
   public static  void data_process(List<trainData>list,int jiqi ,String pageName) {
       if(pageName.equals("all")){
          data_process_chuLi(list,jiqi);
          data_process_liuLiang(list,jiqi);
       }else{
          if(pageName.equals("chuLi"))    data_process_chuLi(list,jiqi);
          if(pageName.equals("liuLiang")) data_process_liuLiang(list,jiqi);
       }

    }
     static void  data_process_chuLi(List<trainData>list,int jiqi){
         try {
             new File("Data_Process/机器" + jiqi + ".xls").delete();
             EasyExcel.write(path + "Data_Process/机器" + jiqi + ".xls", trainData.class).sheet("sheet1").doWrite(list);
             Data_Process process = new Data_Process();
             process.Data_Processing(path + "/机器" + jiqi + ".xls");
         }catch (Exception e){
             e.printStackTrace();
         }
    }
    static void data_process_liuLiang(List<trainData>list,int jiqi){
        try {
            new File("Data_Process_LiuLiang/机器" + jiqi + ".xls").delete();
            EasyExcel.write(path + "Data_Process_LiuLiang/机器" + jiqi + ".xls", trainData.class).sheet("sheet1").doWrite(list);
            Data_Process_LiuLiang process = new Data_Process_LiuLiang();
            process.Data_Processing_LiuLiang(path + "/机器" + jiqi + ".xls");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 拟合
     * @param json
     * @throws Exception
     */
    public static void niHe(JSONObject json) throws  Exception{
        // 出力模型
        if(json.getString("pageName").equals("chuLi")){
            // -- i表示什么?
            new Class1().Data_Fitting(json.getIntValue("jiqi"),
                    path+"Data_Process/机器" + json.getString("jiqi") + "_data_processed.xls",
                    json.get("yuanShu"),//Hn
                    json.get("xueXi"),//,Lr,//xueXi
                    json.get("jingDu"),//Np,
                    json.get("ciShu"),//Mi,
                    json.get("yangBen"),//TSn,
                    0.9
            );
        }
        // 耗流量模型
        if(json.getString("pageName").equals("liuLiang")){
            // -- i表示什么?
          new Data_Fit_LiuLiang().Data_Fitting_LiuLiang(json.getIntValue("jiqi"),
                  path+"Data_Process/机器" + json.getString("jiqi") + "_data_processed.xls",
                  json.get("yuanShu"),//Hn
                  json.get("xueXi"),//,Lr,//xueXi
                  json.get("jingDu"),//Np,
                  json.get("ciShu"),//Mi,
                  json.get("yangBen"),//TSn,
                  0.9);
        }

    }

    public static void SaveModel(int jiqi,String pageName) throws  Exception{
      if(pageName.equals("chuLi"))  {
          if(jiqi==1) new Save_Model_1.Class1().Save_Model_1();
          if(jiqi==2) new Save_Model_2.Class1().Save_Model_2();
      }
        if(pageName.equals("liuLiang"))  {
            if(jiqi==1) new Save_LiuLiang_Model_1().Save_Model_LiuLiang1();
            if(jiqi==2) new Save_LiuLiang_Model_2().Save_Model_LiuLiang2();
        }

    }









}
