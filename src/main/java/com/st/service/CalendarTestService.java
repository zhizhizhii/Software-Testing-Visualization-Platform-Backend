package com.st.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.st.entity.CalendarCase;
import com.st.request.CalendarTestItem;
import com.st.view.CalendarTestItemView;
import com.st.view.CalendarTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalendarTestService {
    private static boolean judgeDateFormat(String s){
        String regEx = "^(\\d{4})-(\\d{1,2})-(\\d{1,2})$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
    private static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }
    private void WrongAnsWithFile(CalendarCase item, String expectation, String info){
        String preInfo = item.getInfo() == null ? "" : item.getInfo();
        item.setActual("-1");
        item.setInfo(preInfo + info + ";");
        String result = expectation.equals(-1.0) ? "测试通过":"测试未通过";
        item.setTest_result(result);
        item.setTest_time(new Date());
    }
    public Workbook handleFile(MultipartFile file) throws Exception{
        boolean WrongFlag = false;
        byte [] byteArr = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        //设置表格格式
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<CalendarCase> calendarCases;
        calendarCases = ExcelImportUtil.importExcel(inputStream,CalendarCase.class,params);
        for(CalendarCase item : calendarCases){
            WrongFlag = false;
            //对于预期输出的判定
            String expectation = item.getExpectation();
            if(!(judgeDateFormat(expectation) || expectation.equals("-1"))){
                WrongAnsWithFile(item, expectation,"预期输出格式错误");
                WrongFlag = true;
            }
            //对于年份的判定
            int year = 2021;
            try{
                year = Integer.parseInt(item.getYear());
                if(!(year>=1970&&year<=9999)){
                    WrongAnsWithFile(item,expectation,"年份有效输入范围为1970~9999");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithFile(item,expectation,"输入的年份非数字");
                WrongFlag = true;
            }
            //对于月份的判定
            int month = 6;
            try{
                month = Integer.parseInt(item.getMonth());
                if(!(month>=1&&month<=12)){
                    WrongAnsWithFile(item,expectation, "月份有效输入范围为1~12");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithFile(item,expectation,"输入的月份非数字");
                WrongFlag = true;
            }
            int actual_day = getDaysByYearMonth(year,month);
            int day = 1;
            try{
                day = Integer.parseInt(item.getDay());
                if(!(day>=1&&day<=actual_day)){
                    WrongAnsWithFile(item,expectation,
                            "天数的有效输入范围为1~" + actual_day);
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithFile(item,expectation,"输入的天数非数字");
                WrongFlag = true;
            }
            if(WrongFlag){
                continue;
            }
            String actual_date = year+"-"+month+"-"+day;
            item.setActual(actual_date);
            item.setInfo("程序正常输出");
            String result = expectation.equals(actual_date) ? "测试通过":"测试未通过";
            item.setTest_result(result);
            item.setTest_time(new Date());
        }
        return ExcelExportUtil.exportExcel(new ExportParams("测试结果","测试结果"), CalendarCase.class,calendarCases);
    }
    private void WrongAnsWithNormalRequest(CalendarTestItemView item, String expectation, String info){
        String preInfo = item.getInfo() == null ? "" : item.getInfo();
        item.setActual("-1");
        item.setInfo(preInfo + info + ";");
        String result = expectation.equals("-1") ? "测试通过":"测试未通过";
        item.setTest_result(result);
        item.setTest_time(new Date());
    }
    public CalendarTestView doTest(List<CalendarTestItem> list){
        boolean WrongFlag;
        //新建返回对象
        CalendarTestView calendarTestView = new CalendarTestView();
        List<CalendarTestItemView> viewList = new ArrayList<CalendarTestItemView>();
        for(CalendarTestItem item : list){
            CalendarTestItemView calendarTestItemView = new CalendarTestItemView();
            WrongFlag = false;
            //对于预期输出的判定
            String expectation = item.getExpectation();
            if(!(judgeDateFormat(expectation) || expectation.equals("-1"))){
                WrongAnsWithNormalRequest(calendarTestItemView, expectation,"预期输出格式错误");
                WrongFlag = true;
            }
            int year = 2021;
            try{
                year = Integer.parseInt(item.getYear());
                if(!(year>=1900&year<=2100)){
                    WrongAnsWithNormalRequest(calendarTestItemView,expectation,"年份有效输入范围为1900~2100");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(calendarTestItemView,expectation,"输入的年份非数字");
                WrongFlag = true;
            }
            int month = 6;
            try{
                month = Integer.parseInt(item.getMonth());
                if(!(month>=1&&month<=12)){
                    WrongAnsWithNormalRequest(calendarTestItemView,expectation, "月份有效输入范围为1~12");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(calendarTestItemView,expectation,"输入的月份非数字");
                WrongFlag = true;
            }
            int actual_day = getDaysByYearMonth(year,month);
            int day = 1;
            try{
                day = Integer.parseInt(item.getDay());
                if(!(day>=1&&day<=actual_day)){
                    WrongAnsWithNormalRequest(calendarTestItemView,expectation,
                            "天数的有效输入范围为1~" + actual_day);
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(calendarTestItemView,expectation,"输入的天数非数字");
                WrongFlag = true;
            }
            if(WrongFlag){
                calendarTestItemView.setId(item.getId());
                viewList.add(calendarTestItemView);
                continue;
            }
            //至此，所有参数格式与数值已经合理
            String actual_date = year+"-"+month+"-"+day;
            calendarTestItemView.setId(item.getId());
            calendarTestItemView.setActual(actual_date);
            calendarTestItemView.setInfo("程序正常输出");
            String result = expectation.equals(actual_date) ? "测试通过":"测试未通过";
            calendarTestItemView.setTest_result(result);
            calendarTestItemView.setTest_time(new Date());
            viewList.add(calendarTestItemView);
        }
        calendarTestView.setCalendarTestItemViews(viewList);
        return calendarTestView;
    }
}
