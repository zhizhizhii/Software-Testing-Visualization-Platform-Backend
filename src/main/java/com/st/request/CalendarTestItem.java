package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CalendarTestItem {
    private String id;

    @JsonProperty("year")
    private String year;

    @JsonProperty("month")
    private String month;

    @JsonProperty("day")
    private String day;

    @JsonProperty("expectation")
    private String expectation;

}
