package com.maven.tc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "登录返回")
public class LoginVO {

    @Schema(description = "JWT令牌")
    private String token;

    @Schema(description = "用户信息")
    private Object userInfo;
}