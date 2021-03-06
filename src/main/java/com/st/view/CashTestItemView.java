package com.st.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CashTestItemView {
    private String id;
    private Double actual;
    private String info;
    private String test_result;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date test_time;
}
