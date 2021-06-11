package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SalesTestItem {
    private String id;

    @JsonProperty("M")
    private String M;

    @JsonProperty("I")
    private String I;

    @JsonProperty("P")
    private String P;

    @JsonProperty("predict")
    private String predict;

    @JsonProperty("pre_amount")
    private String pre_amount;

    @JsonProperty("pre_earn")
    private String pre_earn;
}


