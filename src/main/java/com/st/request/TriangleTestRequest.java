package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TriangleTestRequest {

    @JsonProperty("triangle_test_list")
    private List<TriangleTestItem> triangleTestItemList;
}
