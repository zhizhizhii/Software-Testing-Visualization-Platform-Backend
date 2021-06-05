package com.st.controller;

import com.st.request.CashTestRequest;
import com.st.service.CashTestService;
import com.st.view.CashTestItemView;
import com.st.view.CashTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping(path="/api/cash")
public class CashTestController {

    @Autowired
    CashTestService cashTestService;

    @PostMapping(path="/test")
    @CrossOrigin(value = "http://localhost:8080", maxAge = 1800, allowedHeaders = "*")
    @ResponseBody
    public CashTestView cashTest(@RequestBody CashTestRequest cashTestRequest){
        return cashTestService.doTest(cashTestRequest.getYear()
        ,cashTestRequest.getMonth()
        ,cashTestRequest.getCashTestList());
    }
    @PostMapping(path="/upload")
    @CrossOrigin(value = "http://localhost:8080", maxAge = 1800, allowedHeaders = "*")
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Workbook workbook = cashTestService.handleFile(file);
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition","attachment; filename="+ URLEncoder.encode("1.xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
