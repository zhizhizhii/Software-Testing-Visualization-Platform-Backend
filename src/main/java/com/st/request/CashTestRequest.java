package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CashTestRequest {

    private String year;
    private String month;

    @JsonProperty("cash_test_list")
    private List<CashTestItem> cashTestList;

}
