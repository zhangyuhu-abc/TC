package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "添加面试结果请求")
public class AddInterviewResultDTO {

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "面试类型（一面/二面/霸面）")
    private String type;

    @Schema(description = "面试状态")
    private String status;

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "备注")
    private String remark;
}