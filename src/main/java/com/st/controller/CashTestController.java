package com.st.controller;

import com.st.request.CashTestRequest;
import com.st.service.CashTestService;
import com.st.view.CashTestItemView;
import com.st.view.CashTestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
