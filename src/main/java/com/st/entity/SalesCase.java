package com.st.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;


@Data
@ExcelTarget("salescase")
public class SalesCase {

    @Excel(name="测试用例编号")
    private String id;
    //    @Excel(name="月份",format = "yyyy-MM")
//    private Date month;
    @Excel(name="销售的主机数量M（台）")
    private String machine;
    @Excel(name="销售的显示器数量I（台）")
    private String inspector;
    @Excel(name="销售的外设数量P（套）")
    private String peripheral;
    @Excel(name="预计状态（正常/错误）")
    private String predict;
    @Excel(name="预计销售额（元）")
    private String pre_amount;
    @Excel(name="预计佣金（元）")
    private String pre_earn;
    @Excel(name="实际状态（正常/错误）")
    private String actual;
    @Excel(name="实际销售额（元））")
    private String amount;
    @Excel(name="实际佣金（元）")
    private String earn;
    @Excel(name="测试结果")
    private String test_result;
    @Excel(name="测试时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date test_time;

}
