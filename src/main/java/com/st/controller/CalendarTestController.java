package com.st.controller;

import com.st.request.CalendarTestRequest;
import com.st.service.CalendarTestService;
import com.st.view.CalendarTestView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping(path="/api/calendar")
public class CalendarTestController {
    @Autowired
    CalendarTestService calendarTestService;

    @PostMapping(path="/test")
    @ResponseBody
    public CalendarTestView calendarTest(@RequestBody CalendarTestRequest calendarTestRequest){
        return calendarTestService.doTest(calendarTestRequest.getCalendarTestList());
    }

    @PostMapping(path="/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Workbook workbook = calendarTestService.handleFile(file);
        response.setContentType("application/force-download");
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        String mainDate = df.format(new Date());
        response.setHeader("Content-Disposition","attachment; filename="+ URLEncoder.encode("万年历问题测试报告-"+mainDate+".xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
