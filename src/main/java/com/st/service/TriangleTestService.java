package com.st.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.aliyuncs.utils.StringUtils;
import com.st.entity.CashCase;
import com.st.entity.TriangleCase;
import com.st.request.TriangleTestItem;
import com.st.view.TriangleTestItemView;
import com.st.view.TriangleTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TriangleTestService {

    private class triangle_result{
        public String actual;
        public String info;
    }

    public Workbook handleFile(MultipartFile file) throws Exception {
        byte [] byteArr = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<TriangleCase> triangleCases = ExcelImportUtil.importExcel(inputStream,TriangleCase.class,params);
        triangleCases.forEach(System.out::println);
        for(TriangleCase item : triangleCases){
            TriangleTestService.triangle_result r = triangelTest(item.getOne(), item.getTwo(), item.getThree());
            item.setActual(r.actual);
            item.setInfo(r.info);
            String ans = item.getExpectation().equals(r.actual)  ? "测试通过":"测试未通过";
            item.setTest_result(ans);
            item.setTest_time(new Date());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试结果","而是结果"),TriangleCase.class,triangleCases);
        return workbook;
    }
    public TriangleTestView doTest(List<TriangleTestItem> list){
        TriangleTestView triangleTestView = new TriangleTestView();
        List<TriangleTestItemView> viewList = new ArrayList<>();
        for(TriangleTestItem item : list){
            TriangleTestItemView triangleTestItemView = new TriangleTestItemView();
            triangle_result result = triangelTest(item.getA(),item.getB(),item.getC());
            triangleTestItemView.setId(item.getId());
            triangleTestItemView.setActual(result.actual);
            triangleTestItemView.setInfo(result.info);
            triangleTestItemView.setTest_time(new Date());
            viewList.add(triangleTestItemView);
        }
        triangleTestView.setTriangleTestItemViews(viewList);
        return triangleTestView;
    }

    private triangle_result triangelTest(String strA, String strB, String strC){
        triangle_result ans = new triangle_result();
        Integer A,B,C;
        try{
            if(StringUtils.isEmpty(strA)||StringUtils.isEmpty(strB)||StringUtils.isEmpty(strC)){
                ans.actual="error";
                ans.info="输入不可为空";
                return ans;
            }
            A = Integer.valueOf(strA).intValue();
            B = Integer.valueOf(strB).intValue();
            C = Integer.valueOf(strC).intValue();
        }catch (NumberFormatException e){
            ans.actual="error";
            ans.info="输入格式错误";
            return ans;
        }
        if(!(A>0&&A<=256)||!(B>0&&B<=256)||!(C>0&&C<=256)){
            ans.actual="不构成三角形";
            ans.info="边的取值超出允许范围";
            return ans;
        }
        if(!(A<B+C)||!(B<A+C)||!(C<A+B)){
            ans.actual="不构成三角形";
            ans.info="程序正常输出";
            return ans;
        }
        if(A==B&&A==C&&B==C){
            ans.actual="等边三角形";
            ans.info="程序正常输出";
            return ans;
        }else if(A==B||B==C||A==C){
            ans.actual="等腰三角形";
            ans.info="程序正常输出";
            return ans;
        }

        ans.actual="一般三角形";
        ans.info="程序正常输出";
        return ans;

    }
}
