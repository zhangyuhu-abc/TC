package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改霸面信息请求")
public class UpdateWalkInInterviewDTO {

    @Schema(description = "霸面状态")
    private String status;

    @Schema(description = "备注")
    private String remark;
}