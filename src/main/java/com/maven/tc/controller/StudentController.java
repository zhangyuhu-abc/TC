package com.maven.tc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maven.tc.dto.ApplicationDTO;
import com.maven.tc.dto.LoginDTO;
import com.maven.tc.dto.RegisterDTO;
import com.maven.tc.dto.ResetPasswordDTO;
import com.maven.tc.dto.UpdatePasswordDTO;
import com.maven.tc.dto.WalkInInterviewDTO;
import com.maven.tc.service.ApplicationService;
import com.maven.tc.service.NoticeService;
import com.maven.tc.service.SecondInterviewService;
import com.maven.tc.service.StudentService;
import com.maven.tc.service.WalkInInterviewService;
import com.maven.tc.utils.JwtUtils;
import com.maven.tc.utils.Result;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.LoginVO;
import com.maven.tc.vo.NoticeVO;
import com.maven.tc.vo.SecondInterviewVO;
import com.maven.tc.vo.StudentVO;
import com.maven.tc.vo.WalkInInterviewVO;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    private SecondInterviewService secondInterviewService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private WalkInInterviewService walkInInterviewService;

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
        StudentVO student = studentService.login(dto.getUsername(), dto.getPassword());//调用学生服务登录方法
        if (student == null) {
            return Result.error("学号或密码错误");
        }
        String token = JwtUtils.generateToken(student.getId(), "student");//使用 JWT 工具类生成访问令牌
        return Result.success("登录成功", new LoginVO(token, student));//将 Token 和学生信息封装到 LoginVO 中返回
    }

    @PostMapping("/apply")
    @Operation(summary = "报名")
    public Result<String> apply(@RequestBody ApplicationDTO dto) {
        applicationService.apply(dto);
        return Result.success("报名成功");
    }

    @PostMapping("/walkInApply")
    @Operation(summary = "报名霸面")
    public Result<String> walkInApply(@RequestBody WalkInInterviewDTO dto) {
        try {
            walkInInterviewService.studentWalkInApply(dto);
            return Result.success("霸面报名成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/interviewResult/{studentId}")
    @Operation(summary = "查看一面结果")
    public Result<InterviewResultVO> getInterviewResult(@PathVariable String studentId) {
        InterviewResultVO result = studentService.getInterviewResult(studentId);//调用学生服务获取面试结果方法
        if (result == null) {
            return Result.error("暂无一面结果");
        }
        return Result.success(result);
    }

    @GetMapping("/secondInterviewResult/{studentId}")
    @Operation(summary = "查看二面结果")
    public Result<SecondInterviewVO> getSecondInterviewResult(@PathVariable String studentId) {
        SecondInterviewVO result = studentService.getSecondInterviewResult(studentId);
        if (result == null) {
            return Result.error("暂无二面结果");
        }
        return Result.success(result);
    }

    @GetMapping("/walkInInterviewResult/{studentId}")
    @Operation(summary = "查看霸面结果")
    public Result<WalkInInterviewVO> getWalkInInterviewResult(@PathVariable String studentId) {
        WalkInInterviewVO result = studentService.getWalkInInterviewResult(studentId);
        if (result == null) {
            return Result.error("暂无霸面结果");
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

    @GetMapping("/notices")
    @Operation(summary = "查看通知列表")
    public Result<IPage<NoticeVO>> getNotices(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String type) {
        return Result.success(noticeService.getNotices(current, size, type));
    }

    @GetMapping("/notice/{id}")
    @Operation(summary = "查看通知详情")
    public Result<NoticeVO> getNoticeDetail(@PathVariable Long id) {
        NoticeVO vo = noticeService.getNoticeDetail(id);
        if (vo == null) {
            return Result.error("通知不存在");
        }
        return Result.success(vo);
    }

    @PutMapping("/resetPassword")
    @Operation(summary = "重置密码（忘记密码）")
    public Result<String> resetPassword(@RequestBody ResetPasswordDTO dto) {
        try {
            boolean success = studentService.resetPassword(dto.getStudentId(), dto.getNewPassword());
            if (success) {
                return Result.success("密码重置成功");
            }
            return Result.error("密码重置失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/updatePassword")
    @Operation(summary = "修改密码（记得旧密码）")
    public Result<String> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        try {
            boolean success = studentService.updatePassword(dto.getStudentId(), dto.getOldPassword(), dto.getNewPassword());
            if (success) {
                return Result.success("密码修改成功");
            }
            return Result.error("密码修改失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}