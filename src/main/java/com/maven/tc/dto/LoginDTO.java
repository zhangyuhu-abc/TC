package com.maven.tc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class LoginDTO {

    @Schema(description = "用户名/学号")
    private String username;

    @Schema(description = "密码")
    private String password;
}