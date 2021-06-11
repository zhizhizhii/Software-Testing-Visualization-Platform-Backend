package com.st.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CalendarTestRequest {

    @JsonProperty("calendar_test_list")
    private List<CalendarTestItem> calendarTestList;

}
