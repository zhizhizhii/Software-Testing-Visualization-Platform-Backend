package com.st.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;

@Data
@ExcelTarget("calendarcase")
public class CalendarCase {
    @Excel(name="测试用例编号")
    private String id;
    @Excel(name="年份")
    private String year;
    @Excel(name="月份")
    private String month;
    @Excel(name="天数")
    private String day;
    @Excel(name="预期输出")
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
