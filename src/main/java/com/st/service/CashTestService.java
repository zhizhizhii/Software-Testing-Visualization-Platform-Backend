package com.st.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.st.entity.CashCase;
import com.st.request.CashTestItem;
import com.st.view.CashTestItemView;
import com.st.view.CashTestView;
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
    public Workbook handleFile(MultipartFile file) throws Exception {
        byte [] byteArr = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<CashCase> cashCases = ExcelImportUtil.importExcel(inputStream,CashCase.class,params);
        cashCases.forEach(System.out::println);
        for(CashCase item : cashCases){
            result r = test(31,item.getMinute(),item.getTimes());
            item.setActual(r.actual);
            item.setInfo(r.info);
            String result = item.getExpectation() == r.actual ? "测试通过":"测试未通过";
            item.setTest_result(result);
            item.setTest_time(new Date());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试结果","而是结果"),CashCase.class,cashCases);
        return workbook;
    }

    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();

        a.set(Calendar.YEAR, year);

        a.set(Calendar.MONTH, month - 1);

        a.set(Calendar.DATE, 1);

        a.roll(Calendar.DATE, -1);

        int maxDate = a.get(Calendar.DATE);

        return maxDate;
    }
    private class result{
        public Double actual;
        public String info;
    }

    public CashTestView doTest(String year, String month, List<CashTestItem> list){
        int day = getDaysByYearMonth(Integer.parseInt(year),Integer.parseInt(month));
        CashTestView cashTestView = new CashTestView();
        List<CashTestItemView> viewList = new ArrayList<CashTestItemView>();
        for (CashTestItem item : list)
        {
            CashTestItemView cashTestItemView = new CashTestItemView();
            result r = test(day,item.getX(),item.getY());
            cashTestItemView.setId(item.getId());
            cashTestItemView.setActual(r.actual);
            cashTestItemView.setInfo(r.info);
            viewList.add(cashTestItemView);
        }
        cashTestView.setCashTestItemViews(viewList);
        return cashTestView;
    }

    private result test(Integer day,Integer X, Integer Y){
        result r = new result();
        if(!(X>=0 && X<=day*24*60)){
            r.info = "通话分钟数不在合理范围，合理范围在0~"+day*24*60+"分钟";
            r.actual = -1.0;
            return r;
        }
        if(!(Y>=0 && Y<=11)){
            r.info = "本年度至本月的累计未按时缴费的次数不在合理范围，合理范围在0~11次";
            r.actual = -1.0;
            return r;
        }
        double discount = 1;
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
        else{
            discount = 1;
        }

        r.actual = 25+0.15*X*discount;
        r.info = "程序正常输出";
        return r;
    }

}
