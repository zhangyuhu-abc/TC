package com.maven.tc.controller;

import com.maven.tc.dto.ApplicationDTO;
import com.maven.tc.dto.LoginDTO;
import com.maven.tc.dto.RegisterDTO;
import com.maven.tc.entity.Application;
import com.maven.tc.service.ApplicationService;
import com.maven.tc.service.StudentService;
import com.maven.tc.utils.JwtUtils;
import com.maven.tc.utils.Result;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.LoginVO;
import com.maven.tc.vo.StudentVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result<StudentVO> register(@RequestBody RegisterDTO dto) {
        try {
            StudentVO result = studentService.register(dto);
            return Result.success("注册成功", result);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        StudentVO student = studentService.login(dto.getUsername(), dto.getPassword());
        if (student == null) {
            return Result.error("学号或密码错误");
        }
        String token = JwtUtils.generateToken(student.getId(), "student");
        return Result.success("登录成功", new LoginVO(token, student));
    }

    @PostMapping("/apply")
    @Operation(summary = "报名")
    public Result<String> apply(@RequestBody ApplicationDTO dto) {
        Application application = new Application();
        BeanUtils.copyProperties(dto, application);
        applicationService.save(application);
        return Result.success("报名成功");
    }

    @GetMapping("/interviewResult/{studentId}")
    @Operation(summary = "查看面试结果")
    public Result<InterviewResultVO> getInterviewResult(@PathVariable String studentId) {
        InterviewResultVO result = studentService.getInterviewResult(studentId);
        if (result == null) {
            return Result.error("暂无面试结果");
        }
        return Result.success(result);
    }

    @GetMapping("/directions")
    @Operation(summary = "查看方向介绍")
    public Result<Map<String, Object>> getDirections() {
        return Result.success(studentService.getDirections());
    }

    @GetMapping("/learningPaths")
    @Operation(summary = "查看学习路线")
    public Result<Map<String, Object>> getLearningPaths() {
        return Result.success(studentService.getLearningPaths());
    }
}