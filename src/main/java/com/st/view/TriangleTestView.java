package com.st.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TriangleTestView {

    @JsonProperty("test_result")
    private List<TriangleTestItemView> triangleTestItemViews;
}
