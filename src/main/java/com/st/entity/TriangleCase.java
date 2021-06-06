package com.st.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;

@Data
@ExcelTarget("trianglecase")
public class TriangleCase {

    @Excel(name="测试用例编号")
    private String id;

    @Excel(name="第一条边的值（a）")
    private String one;
    @Excel(name="第二条边的值（b）")
    private String two;
    @Excel(name="第三条边的值（c）")
    private String three;
    @Excel(name="程序预期输出")
    private String expectation;
    @Excel(name="实际输出")
    private String actual;
    @Excel(name="程序运行信息")
    private String info;
    @Excel(name="测试结果")
    private String test_result;
    @Excel(name="测试时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date test_time;

}
