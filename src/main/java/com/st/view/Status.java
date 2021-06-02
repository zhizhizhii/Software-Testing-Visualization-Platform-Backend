package com.st.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Status {

    @ApiModelProperty("状态")
    private Boolean state;

    @ApiModelProperty("提示信息")
    private String msg;

}
