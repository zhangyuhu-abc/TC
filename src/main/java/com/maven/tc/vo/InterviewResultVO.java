package com.maven.tc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "面试结果")
public class InterviewResultVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "面试状态")
    private String status;

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "备注")
    private String remark;
}