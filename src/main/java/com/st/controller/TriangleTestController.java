package com.st.controller;


import com.st.request.TriangleTestRequest;
import com.st.service.TriangleTestService;
import com.st.view.TriangleTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Controller
@RequestMapping(path = "/api/triangle")
public class TriangleTestController {

    @Autowired
    TriangleTestService triangleTestService;

    @PostMapping(path = "/test")
    @ResponseBody
    public TriangleTestView triangleTest(@RequestBody TriangleTestRequest triangleTestRequest){
        return triangleTestService.doTest(triangleTestRequest.getTriangleTestItemList());
    }

    @PostMapping(path = "/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Workbook workbook = triangleTestService.handleFile(file);
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition","attachment; filename="+ URLEncoder.encode("1.xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

}
