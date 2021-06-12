package com.st.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.st.entity.SalesCase;
import com.st.request.SalesTestItem;
import com.st.view.SalesTestItemView;
import com.st.view.SalesTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SalesTestService {
    public Workbook handleFile(MultipartFile file) throws Exception {
        byte [] byteArr = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<SalesCase> salesCases = ExcelImportUtil.importExcel(inputStream,SalesCase.class,params);
        String result;
        for(SalesCase item : salesCases){
            result r = test(item.getMachine(),item.getInspector(),item.getPeripheral());
            item.setActual(r.actual);
            item.setAmount(r.amount);
            item.setEarn(r.earn);
            if( (item.getPredict()).equals(r.actual)){
                if( (item.getPre_amount()).equals(r.amount)){
                    result="测试通过";
                }
                else{
                    result="测试未通过";
                }
            }
            else{
                result="测试未通过";
            }
            item.setTest_result(result);
            item.setTest_time(new Date());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试结果","而是结果"),SalesCase.class,salesCases);
        return workbook;
    }

    private class result{
        public String actual;
        public String amount;
        public String earn;
    }

    public SalesTestView doTest(List<SalesTestItem> list){
        SalesTestView salesTestView = new SalesTestView();
        List<SalesTestItemView> viewList = new ArrayList<SalesTestItemView>();
        for (SalesTestItem item : list)
        {
            SalesTestItemView salesTestItemView = new SalesTestItemView();
            result r = test(item.getM(),item.getI(),item.getP());
            salesTestItemView.setId(item.getId());
            salesTestItemView.setActual(r.actual);
            salesTestItemView.setAmount(r.amount);
            salesTestItemView.setEarn(r.earn);
            salesTestItemView.setTest_time(new Date());
            viewList.add(salesTestItemView);
        }
        salesTestView.setSalesTestItemViews(viewList);
        return salesTestView;
    }

    private result test(String M, String I,String P){
        result r = new result();
        if(!(isOne(M)&&isOne(M)&&isOne(P))){
            r.actual="错误";
            r.amount="";
            r.earn="";
            return r;
        }
        try{
            int intM=Integer.parseInt(M);
            int intI=Integer.parseInt(I);
            int intP=Integer.parseInt(P);

            if(!(intM>=1&&intM<71)){
                r.actual="错误";
                r.amount="";
                r.earn="";
                return r;
            }
            if(!(intI>=1&&intI<=80)){
                r.actual="错误";
                r.amount="";
                r.earn="";
                return r;
            }
            if(!(intP>=1&&intP<=90)){
                r.actual="错误";
                r.amount="";
                r.earn="";
                return r;
            }
            int amount=25*intM+30*intI+45*intP;
            r.amount=Integer.toString(25*intM+30*intI+45*intP);
            if(amount<=1000){
                r.actual="正常";
                r.earn=String.valueOf(amount*0.1);
                return r;
            }
            if(amount<=1800){
                r.actual="正常";
                r.earn=String.valueOf(amount*0.15);
                return r;
            }
            if(amount>1800){
                r.actual="正常";
                r.earn=String.valueOf(amount*0.2);
                return r;
            }
        }catch(NumberFormatException e){

        }
        r.actual="空白";
        return r;
    }

    private static boolean isOne(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
