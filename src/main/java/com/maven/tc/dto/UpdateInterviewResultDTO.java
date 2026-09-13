package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改一面信息请求")
public class UpdateInterviewResultDTO {

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "面试状态")
    private String status;

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "备注")
    private String remark;
}