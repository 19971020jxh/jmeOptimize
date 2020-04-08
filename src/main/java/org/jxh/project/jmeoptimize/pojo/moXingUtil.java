package org.jxh.project.jmeoptimize.pojo;

import Data_Fitting.Class1;
import Data_Fitting.Data_FittingMCRFactory;
import Data_Processing.Data_Process;
import Save_Model_2.Save_Model_2MCRFactory;
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
   public static  void data_process(List<trainData>list,int jiqi) {
        try {
            new File("Data_Process/机器" + jiqi + ".xls").delete();
            EasyExcel.write(path + "Data_Process/机器" + jiqi + ".xls", trainData.class).sheet("sheet1").doWrite(list);
            Data_Process process = new Data_Process();
            process.Data_Processing(path + "/机器" + jiqi + ".xls");
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
        new Class1().Data_Fitting(1,
                path+"Data_Process/机器" + json.getString("jiqi") + "_data_processed.xls",
                json.get("yuanShu"),//Hn
                json.get("xueXi"),//,Lr,//xueXi
                json.get("jingDu"),//Np,
                json.get("ciShu"),//Mi,
                json.get("yangBen"),//TSn,
                0.9
        );

    }

    public static void SaveModel(int jiqi) throws  Exception{
      if(jiqi==1) new Save_Model_1.Class1().Save_Model_1();
      if(jiqi==2) new Save_Model_2.Class1().Save_Model_2();
    }









}
