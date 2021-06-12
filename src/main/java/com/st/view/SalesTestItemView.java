package com.st.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SalesTestItemView {
    private String id;
    private String actual;
    private String amount;
    private String earn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date test_time;
}
