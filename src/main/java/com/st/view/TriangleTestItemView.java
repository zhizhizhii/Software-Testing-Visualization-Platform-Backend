package com.st.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TriangleTestItemView {
    private String id;
    private String actual;
    private String info;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date test_time;
}
