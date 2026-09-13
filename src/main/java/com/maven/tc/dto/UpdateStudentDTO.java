package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改学生信息请求")
public class UpdateStudentDTO {


    @Schema(description = "姓名")
    private String name;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "专业")
    private String major;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;
}