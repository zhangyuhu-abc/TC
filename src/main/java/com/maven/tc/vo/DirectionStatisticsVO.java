package com.maven.tc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "方向统计信息")
public class DirectionStatisticsVO {

    @Schema(description = "面试方向")
    private String direction;

    @Schema(description = "报名人数")
    private Long applyCount;

    @Schema(description = "一面通过人数")
    private Long firstPassCount;

    @Schema(description = "二面通过人数")
    private Long secondPassCount;

    @Schema(description = "霸面通过人数")
    private Long walkInPassCount;
}