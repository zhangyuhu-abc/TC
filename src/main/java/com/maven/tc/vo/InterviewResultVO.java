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

    @Schema(description = "一面状态")
    private String status;

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "二面状态")
    private String secondStatus;

    @Schema(description = "二面备注")
    private String secondRemark;

    @Schema(description = "霸面状态")
    private String walkInStatus;

    @Schema(description = "霸面备注")
    private String walkInRemark;

    @Schema(description = "是否被录取")
    private Boolean admitted;
}