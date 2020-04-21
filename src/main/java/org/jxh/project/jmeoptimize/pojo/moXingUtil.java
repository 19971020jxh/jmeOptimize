package org.jxh.project.jmeoptimize.pojo;

import Characteristic_Curve_Show.Characteristic_Curve;
import Chuli_Net_Saveas.Chuli_Net_Save;
import Data_Fitting.Class1;
import Data_Fitting.Data_FittingMCRFactory;
import Data_Fitting_Chuli.Data_Fit_Chuli;
//import Data_Fitting_LiuLiang.Data_Fit_LiuLiang;
import Data_Fitting_Liuliang.Data_Fit_Liuliang;
import Data_Processing.Data_Process;
import Data_Processing_Chuli.Data_Process_Chuli;
//import Data_Processing_LiuLiang.Data_Process_LiuLiang;
import Data_Processing_Liuliang.Data_Process_Liuliang;
import Liuliang_Net_Saveas.Liuliang_Net_Save;
import Save_Model_2.Save_Model_2MCRFactory;
import Save_Model_LiuLiang1.Save_LiuLiang_Model_1;
import Save_Model_LiuLiang2.Save_LiuLiang_Model_2;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.List;

/**
 * 当特征数据发生改变时,重新训练模型!
 */
public   class  moXingUtil {
    static String  path=System.getProperty("user.dir");

    /**
     *
     *
     * @param jiqi
     */
   public static  void data_process(String jiqi , String pageName, InputStream stream) throws  Exception{
       FileOutputStream outputStream=null;
       try {
           // -> 生成临时文件.
           File excel = new File(path + "/JZ_From_Databases/NO" + jiqi + ".xls");
           excel.createNewFile();
           outputStream = new FileOutputStream(excel);
           int read = 0;
           byte[] buffer = new byte[1024];
           while ((read = stream.read(buffer)) != -1) {
               outputStream.write(buffer, 0, read);
           }
           //   data_Process
           if(pageName.equals("chuLi")) {
               Data_Process_Chuli process_chuli= new Data_Process_Chuli();
               process_chuli.Data_Processing_Chuli(path + "/Data_Process_Chuli/NO" + jiqi + ".xls");
           }
           if(pageName.equals("liuLiang")){
               Data_Process_Liuliang process_liuLiang=new Data_Process_Liuliang();
               process_liuLiang.Data_Processing_Liuliang(path + "/Data_Process_LiuLiang/NO" + jiqi + ".xls");
           }
           //- > 删除临时文件
           excel.delete();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           outputStream.close();
           stream.close();
       }
    }
//     static void  data_process_chuLi(String jiqi,InputStream stream) throws  Exception{
//         FileOutputStream outputStream=null;
//         try {
//             // -> 生成临时文件.
//             File excel = new File(path + "/JZ_From_Databases/NO" + jiqi + ".xls");
//             excel.createNewFile();
//             outputStream = new FileOutputStream(excel);
//             int read = 0;
//             byte[] buffer = new byte[1024];
//             while ((read = stream.read(buffer)) != -1) {
//                 outputStream.write(buffer, 0, read);
//             }
//            //   data_Process
//             Data_Process_Chuli process_chuli= new Data_Process_Chuli();
//             process_chuli.Data_Processing_Chuli(path + "/JZ_From_Databases/NO" + jiqi + ".xls");
//             //- > 删除临时文件
//              excel.delete();
//         }catch (Exception e){
//             e.printStackTrace();
//         }finally {
//             outputStream.close();
//             stream.close();
//         }
//
//
//    }
//    static void data_process_liuLiang(List<trainData>list,String jiqi){
//        try {
//            new File("Data_Process_LiuLiang/NO"+jiqi.toUpperCase()+"_Characteristic_curve_data.xls").delete();
//        //    EasyExcel.write(path + "Data_Process_LiuLiang/机器" + jiqi + ".xls", trainData.class).sheet("sheet1").doWrite(list);
//            Data_Process_LiuLiang process = new Data_Process_LiuLiang();
//            process.Data_Processing_LiuLiang(path + "/JZ_From_Databases/NO"+jiqi.toUpperCase()+"_Characteristic_curve_data.xls");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    /**
     * 拟合
     * @param json
     * @throws Exception
     */
    public static void niHe(JSONObject json) throws  Exception{
        // 出力模型
        if(json.getString("pageName").equals("chuLi")){

            Data_Fit_Chuli chuli=new Data_Fit_Chuli();
            int i=0;
            chuli.Data_Fitting_Chuli(
                    i, // ----- > i 是什么
                 path+"/Data_Process_Chuli/NO"+ json.getString("jiqi").toUpperCase()+"_data_processed.xls",
                    json.get("yuanShu"),//Hn
                    json.get("xueXi"),//,Lr,//xueXi
                    json.get("jingDu"),//Np,
                    json.get("ciShu"),//Mi,
                    json.get("yangBen"),//TSn,
                    json.get("wenDing")
            );


//            new Class1().Data_Fitting(json.getIntValue("jiqi"),
//                    path+"Data_Process/机器" + json.getString("jiqi") + "_data_processed.xls",
//                    json.get("yuanShu"),//Hn
//                    json.get("xueXi"),//,Lr,//xueXi
//                    json.get("jingDu"),//Np,
//                    json.get("ciShu"),//Mi,
//                    json.get("yangBen"),//TSn,
//                    0.9
//            );


        }
        // 耗流量模型
        if(json.getString("pageName").equals("liuLiang")){

            Data_Fit_Liuliang liuLiang=new Data_Fit_Liuliang();
            int i=0;
            liuLiang.Data_Fitting_Liuliang(
                    i, // --- > ???
                    path+"/Data_Process_LiuLiang/NO"+ json.getString("jiqi").toUpperCase()+"_data_processed.xls",
                    json.get("yuanShu"),//Hn
                    json.get("xueXi"),//,Lr,//xueXi
                    json.get("jingDu"),//Np,
                    json.get("ciShu"),//Mi,
                    json.get("yangBen"),//TSn,
                    json.get("wenDing")
            );
            // -- i表示什么?
//          new Data_Fit_LiuLiang().Data_Fitting_LiuLiang(json.getIntValue("jiqi"),
//                  path+"Data_Process/机器" + json.getString("jiqi") + "_data_processed.xls",
//                  json.get("yuanShu"),//Hn
//                  json.get("xueXi"),//,Lr,//xueXi
//                  json.get("jingDu"),//Np,
//                  json.get("ciShu"),//Mi,
//                  json.get("yangBen"),//TSn,
//                  0.9);
        }

    }

    public static void SaveModel(String jiqi,String pageName) throws  Exception{
      if(pageName.equals("chuLi"))  {
            new Chuli_Net_Save().Chuli_Net_Saveas(path+"/Data_Fitted_Chuli/NO"+jiqi.toUpperCase()+"_net");
      }
        if(pageName.equals("liuLiang"))  {
            new Liuliang_Net_Save().Liuliang_Net_Saveas(path+"/Data_Fitted_Liuliang/NO"+jiqi.toUpperCase()+"_net");
        }

    }

    /**
     * 效率曲线
     * @param jiqi
     */
  public static void Curve(String jiqi) throws  Exception{
    new Characteristic_Curve().Characteristic_Curve_Show(path+"/JZ_From_Databases/NO"+jiqi+"_Characteristic_curve_data.xls");
  }






}
