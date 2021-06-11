package com.st.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.st.entity.CashCase;
import com.st.request.CashTestItem;
import com.st.view.CashTestItemView;
import com.st.view.CashTestView;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CashTestService {
    private static class result{
        public Double actual;
        public String info;
    }
    private static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }
    private void WrongAnsWithFile(CashCase item, Object expectation, String info){
        String preInfo = item.getInfo() == null ? "" : item.getInfo();
        item.setActual(-1.0);
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
        List<CashCase> cashCases;
        cashCases = ExcelImportUtil.importExcel(inputStream,CashCase.class,params);
        for(CashCase item : cashCases){
            WrongFlag = false;
            //对于预期输出的判定
            Object expectation = item.getExpectation();
            try{
                expectation = Double.parseDouble(item.getExpectation());
            }catch (NumberFormatException e){
                WrongAnsWithFile(item, expectation,"预期输出非数字");
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
            int day = getDaysByYearMonth(year,month);
            int X = 0;
            try{
                X = Integer.parseInt(item.getMinute());
                if(!(X>=0 && X<=day*24*60)){
                    WrongAnsWithFile(item,expectation,"通话分钟数不在合理范围，合理范围在0~"+day*24*60+"分钟");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithFile(item,expectation,"输入的通话分钟数非数字");
                WrongFlag = true;
            }
            int Y = 0;
            try{
                Y = Integer.parseInt(item.getTimes());
                if(!(Y>=0 && Y<=11)){
                    WrongAnsWithFile(item,expectation,"累计未按时缴费的次数不在合理范围，合理范围在0~11次");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithFile(item,expectation,"输入的未缴费次数非数字");
                WrongFlag = true;
            }
            if(WrongFlag){
                continue;
            }
            //至此，所有参数格式与数值已经合理，进行结果测试，
            result r = test(X,Y);
            item.setActual(r.actual);
            item.setInfo(r.info);
            String result = expectation.equals(r.actual) ? "测试通过":"测试未通过";
            item.setTest_result(result);
            item.setTest_time(new Date());
        }
        return ExcelExportUtil.exportExcel(new ExportParams("测试结果","测试结果"),CashCase.class,cashCases);
    }
    private void WrongAnsWithNormalRequest(CashTestItemView item, Object expectation, String info){
        String preInfo = item.getInfo() == null ? "" : item.getInfo();
        item.setActual(-1.0);
        item.setInfo(preInfo + info + ";");
        String result = expectation.equals(-1.0) ? "测试通过":"测试未通过";
        item.setTest_result(result);
        item.setTest_time(new Date());
    }
    public CashTestView doTest(List<CashTestItem> list){
        boolean WrongFlag;
        //新建返回对象
        CashTestView cashTestView = new CashTestView();
        List<CashTestItemView> viewList = new ArrayList<CashTestItemView>();
        for(CashTestItem item : list){
            CashTestItemView cashTestItemView = new CashTestItemView();
            WrongFlag = false;
            //对于预期输出的判定
            Object expectation = item.getExpectation();
            try{
                expectation = Double.parseDouble(item.getExpectation());
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(cashTestItemView, expectation,"预期输出非数字");
                WrongFlag = true;
            }
            //对于年份的判定
            int year = 2021;
            try{
                year = Integer.parseInt(item.getYear());
                if(!(year>=1970&&year<=9999)){
                    WrongAnsWithNormalRequest(cashTestItemView,expectation,"年份有效输入范围为1970~9999");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(cashTestItemView,expectation,"输入的年份非数字");
                WrongFlag = true;
            }
            //对于月份的判定
            int month = 6;
            try{
                month = Integer.parseInt(item.getMonth());
                if(!(month>=1&&month<=12)){
                    WrongAnsWithNormalRequest(cashTestItemView,expectation, "月份有效输入范围为1~12");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(cashTestItemView,expectation,"输入的月份非数字");
                WrongFlag = true;
            }
            int day = getDaysByYearMonth(year,month);
            int X = 0;
            try{
                X = Integer.parseInt(item.getX());
                if(!(X>=0 && X<=day*24*60)){
                    WrongAnsWithNormalRequest(cashTestItemView,expectation,"通话分钟数不在合理范围，合理范围在0~"+day*24*60+"分钟");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(cashTestItemView,expectation,"输入的通话分钟数非数字");
                WrongFlag = true;
            }
            int Y = 0;
            try{
                Y = Integer.parseInt(item.getY());
                if(!(Y>=0 && Y<=11)){
                    WrongAnsWithNormalRequest(cashTestItemView,expectation,"累计未按时缴费的次数不在合理范围，合理范围在0~11次");
                    WrongFlag = true;
                }
            }catch (NumberFormatException e){
                WrongAnsWithNormalRequest(cashTestItemView,expectation,"输入的未缴费次数非数字");
                WrongFlag = true;
            }
            if(WrongFlag){
                cashTestItemView.setId(item.getId());
                viewList.add(cashTestItemView);
                continue;
            }
            //至此，所有参数格式与数值已经合理，进行结果测试，
            result r = test(X,Y);
            cashTestItemView.setId(item.getId());
            cashTestItemView.setActual(r.actual);
            cashTestItemView.setInfo(r.info);
            String result = expectation.equals(r.actual) ? "测试通过":"测试未通过";
            cashTestItemView.setTest_result(result);
            cashTestItemView.setTest_time(new Date());
            viewList.add(cashTestItemView);
        }
        cashTestView.setCashTestItemViews(viewList);
        return cashTestView;
    }
    private result test(Integer X, Integer Y){
        result r = new result();
        Double discount = 1.0;
        if(X>0 && X<=60 && Y<=1){
            discount = 1 - 0.01;
        }
        else if(X>60 && X<=120 && Y<=2){
            discount = 1 - 0.015;
        }
        else if(X>120 && X<=180 && Y<=3){
            discount = 1 - 0.02;
        }
        else if(X>180 && X<=300 && Y<=3){
            discount = 1 - 0.025;
        }
        else if(X>300 && Y<=6){
            discount = 1 - 0.03;
        }

        BigDecimal db1 = new BigDecimal(25 + "");
        BigDecimal db2 = new BigDecimal(X + "");
        BigDecimal db3 = new BigDecimal(discount + "");
        BigDecimal db4 = new BigDecimal(0.15 + "");
        BigDecimal temp = db2.multiply(db3).multiply(db4);
        r.actual = db1.add(temp).doubleValue();
        r.info = "程序正常输出";
        return r;
    }
}
