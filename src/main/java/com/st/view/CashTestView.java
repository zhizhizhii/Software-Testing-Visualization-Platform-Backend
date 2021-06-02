package com.st.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CashTestView {

    @JsonProperty("test_result")
    private List<CashTestItemView> cashTestItemViews;
}
