package com.maven.tc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maven.tc.dto.LoginDTO;
import com.maven.tc.entity.Application;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.entity.Student;
import com.maven.tc.service.AdminService;
import com.maven.tc.service.ApplicationService;
import com.maven.tc.service.InterviewResultService;
import com.maven.tc.service.StudentService;
import com.maven.tc.utils.JwtUtils;
import com.maven.tc.utils.Result;
import com.maven.tc.vo.AdminVO;
import com.maven.tc.vo.ApplicationVO;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private InterviewResultService interviewResultService;

    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        AdminVO admin = adminService.login(dto.getUsername(), dto.getPassword());
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }
        String token = JwtUtils.generateToken(admin.getId(), "admin");
        return Result.success("登录成功", new LoginVO(token, admin));
    }

    @GetMapping("/students")
    @Operation(summary = "查看注册学生")
    public Result<Page<Student>> getStudents(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Student> page = new Page<>(current, size);
        return Result.success(studentService.page(page));
    }

    @PutMapping("/student")
    @Operation(summary = "修改学生信息")
    public Result<String> updateStudent(@RequestBody Student student) {
        studentService.updateById(student);
        return Result.success("修改成功");
    }

    @DeleteMapping("/student/{id}")
    @Operation(summary = "删除学生")
    public Result<String> deleteStudent(@PathVariable Long id) {
        studentService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/applications")
    @Operation(summary = "查看报名学生")
    public Result<IPage<ApplicationVO>> getApplications(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String interviewTime) {
        return Result.success(applicationService.getApplications(current, size, direction, interviewTime));
    }

    @PostMapping("/application")
    @Operation(summary = "添加报名学生")
    public Result<String> addApplication(@RequestBody Application application) {
        applicationService.save(application);
        return Result.success("添加成功");
    }

    @PutMapping("/application")
    @Operation(summary = "修改报名信息")
    public Result<String> updateApplication(@RequestBody Application application) {
        applicationService.updateById(application);
        return Result.success("修改成功");
    }

    @DeleteMapping("/application/{id}")
    @Operation(summary = "删除报名信息")
    public Result<String> deleteApplication(@PathVariable Long id) {
        applicationService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/interviewResults")
    @Operation(summary = "查看学生面试结果")
    public Result<IPage<InterviewResultVO>> getInterviewResults(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String direction) {
        return Result.success(interviewResultService.getInterviewResults(current, size, direction));
    }

    @PutMapping("/interviewResult")
    @Operation(summary = "修改面试结果")
    public Result<String> updateInterviewResult(@RequestBody InterviewResult interviewResult) {
        interviewResultService.updateById(interviewResult);
        return Result.success("修改成功");
    }
}