package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改二面信息请求")
public class UpdateSecondInterviewDTO {

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "二面状态")
    private String status;

    @Schema(description = "二面备注")
    private String remark;
}