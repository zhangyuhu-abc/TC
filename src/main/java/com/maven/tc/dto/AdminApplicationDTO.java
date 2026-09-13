package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员添加/修改报名请求")
public class AdminApplicationDTO {


    @Schema(description = "学生姓名")
    private String name;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "意向方向")
    private String direction;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "面试时间")
    private String interviewTime;
}