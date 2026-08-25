package com.maven.tc.controller;

import com.maven.tc.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("/intro")
    @Operation(summary = "实验室介绍")
    public Result<Map<String, String>> getLabIntro() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", "ThoughtCoding 实验室");
        map.put("founded", "2015年4月");
        map.put("slogan", "以项目为导向、以实践促提升");
        map.put("description", "ThoughtCoding 实验室成立于 2015 年 4 月，由一群富有激情的智能硬件爱好者共同创办。"
                + "实验室秉承\"以项目为导向、以实践促提升\"的理念，在老师的悉心指导下，成员们积极投身于嵌入式、后台、安卓及 Web 等多个方向的协作开发。"
                + "通过跨领域的紧密合作，实验室不断探索新技术、积累实战经验。"
                + "近年来，团队先后参加了\"互联网+ 、全国大学生计算机设计大赛、信创赛、电赛、嵌赛、仿真应用大赛、大挑小挑等多项赛事，并取得了优异的成绩。");
        return Result.success(map);
    }

    @GetMapping("/directions")
    @Operation(summary = "方向学习内容")
    public Result<List<Map<String, String>>> getDirections() {
        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> d1 = new LinkedHashMap<>();
        d1.put("name", "嵌入式开发");
        d1.put("description", "嵌入式开发主要是为特定硬件平台开发专用软件，通常用于微控制器、单片机、IoT设备等。");
        list.add(d1);

        Map<String, String> d2 = new LinkedHashMap<>();
        d2.put("name", "全栈开发");
        d2.put("description", "全栈开发同时涵盖前端与后端开发技术，能够独立完成整套软件系统的开发工作。"
                + "既掌握HTML、CSS、JavaScript等前端技术，负责用户界面搭建、交互效果实现与用户体验优化；"
                + "又精通服务器端逻辑开发、数据库设计与管理、接口开发、业务功能落地等后端核心工作。");
        list.add(d2);

        Map<String, String> d3 = new LinkedHashMap<>();
        d3.put("name", "安卓开发");
        d3.put("description", "安卓开发专注于为Android操作系统平台创建移动应用，覆盖智能手机、平板等设备。");
        list.add(d3);

        return Result.success(list);
    }


    @GetMapping("/awards")
    @Operation(summary = "奖项介绍")
    public Result<List<String>> getAwards() {
        List<String> list = new ArrayList<>();
        list.add("全国仿真创新设计大赛国一");
        list.add("全国大学生计算机设计大赛西北赛区一等奖");
        list.add("数学建模大赛校赛一等奖");
        list.add("互联网+校赛二等奖");
        list.add("24年TI杯电子设计竞赛省一");
        list.add("蓝桥杯C/C++省一");
        list.add("嵌入式系统专题邀请赛（英特尔杯）三等奖");
        return Result.success(list);
    }

    @GetMapping("/directionStats")
    @Operation(summary = "方向介绍")
    public Result<Map<String, Object>> getDirectionStats() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("嵌入式开发", Map.of("memberCount", 15, "projectCount", 8));
        map.put("全栈开发", Map.of("memberCount", 20, "projectCount", 12));
        map.put("安卓开发", Map.of("memberCount", 10, "projectCount", 6));
        return Result.success(map);
    }
}