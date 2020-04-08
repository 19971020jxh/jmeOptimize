package org.jxh.project.jmeoptimize.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class trainData extends BaseRowModel {
    @ExcelProperty(value = {"ID"},index = 0)
    private Integer id;
    @ExcelProperty(value = {"XiaoLv"},index = 1)
    private Integer xiaolv;
    @ExcelProperty(value = {"ShuiTou"},index = 2)
    private Double shuitou;
    @ExcelProperty(value = {"ChuLi"},index = 3)
    private Double chuli;
    @ExcelProperty(value = {"LiuLiang"},index = 4)
    private Double liuliang;
    @ExcelProperty(value = {"ORDER_TR"},index =5)
    private Double ORDER_TR;
    private String[] xiaoLvQuXian;
}
