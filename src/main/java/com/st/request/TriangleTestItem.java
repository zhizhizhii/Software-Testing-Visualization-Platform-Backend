package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TriangleTestItem {
    private String id;

    @JsonProperty("A")
    private String A;

    @JsonProperty("B")
    private String B;

    @JsonProperty("C")
    private String C;
}
