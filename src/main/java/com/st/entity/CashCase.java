package com.st.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;


@Data
@ExcelTarget("cashcase")
public class CashCase {

    @Excel(name="测试用例编号")
    private String id;
//    @Excel(name="月份",format = "yyyy-MM")
//    private Date month;
    @Excel(name="本月的通话分钟数X（分钟）")
    private Integer minute;
    @Excel(name="本年度至本月的累计未按时缴费的次数Y（次）")
    private Integer times;
    @Excel(name="每月的电话总费用预期输出")
    private Double expectation;
    @Excel(name="实际输出")
    private Double actual;
    @Excel(name="程序运行信息")
    private String info;
    @Excel(name="测试结果")
    private String test_result;
    @Excel(name="测试时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date test_time;

}
