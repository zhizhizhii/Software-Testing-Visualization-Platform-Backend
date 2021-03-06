package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CashTestItem {
    private String id;

    @JsonProperty("year")
    private String year;

    @JsonProperty("month")
    private String month;

    @JsonProperty("X")
    private String X;

    @JsonProperty("Y")
    private String Y;

    @JsonProperty("expectation")
    private String expectation;

}
