package com.st.controller;

import com.st.request.SalesTestRequest;
import com.st.service.SalesTestService;
import com.st.view.SalesTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Controller
@RequestMapping(path="/api/sales")
public class SalesTestController {

    @Autowired
    SalesTestService salesTestService;

    @PostMapping(path="/test")
    @ResponseBody
    public SalesTestView salesTest(@RequestBody SalesTestRequest salesTestRequest){
        return salesTestService.doTest(salesTestRequest.getSalesTestList());
    }
    @PostMapping(path="/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Workbook workbook = salesTestService.handleFile(file);
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition","attachment; filename="+ URLEncoder.encode("1.xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
