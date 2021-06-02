package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CashTestItem {
    private String id;

    @JsonProperty("X")
    private Integer X;

    @JsonProperty("Y")
    private Integer Y;

}
