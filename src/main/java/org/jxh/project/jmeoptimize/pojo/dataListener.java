package org.jxh.project.jmeoptimize.pojo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class dataListener extends AnalysisEventListener<trainData> {
    List<trainData> list=new ArrayList<trainData>();

    /**
     * 每解析一条数据都会调用
     * @param trainData
     * @param analysisContext
     */
    @Override
    public void invoke(trainData trainData, AnalysisContext analysisContext) {
        list.add(trainData);
    }
    /**
     * 所有数据解析完会调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
    public List<trainData> getDatas(){
        return  list;
    }
}
