package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesTestRequest {

    @JsonProperty("sales_test_list")
    private List<SalesTestItem> salesTestList;

}
