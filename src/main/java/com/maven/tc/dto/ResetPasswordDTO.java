package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "重置密码请求")
public class ResetPasswordDTO {

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "新密码")
    private String newPassword;
}