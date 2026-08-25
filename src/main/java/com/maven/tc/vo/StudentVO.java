package com.maven.tc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "学生信息")
public class StudentVO {

    @Schema(description = "id")
    private Long id;

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