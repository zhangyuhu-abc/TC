package com.maven.tc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("application")
@Schema(description = "报名信息")
public class Application {

    @TableId(type = IdType.AUTO)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "面试时间")
    private String interviewTime;

    @Schema(description = "学号")
    private String studentId;
}