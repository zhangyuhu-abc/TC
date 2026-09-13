package com.maven.tc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maven.tc.dto.*;
import com.maven.tc.service.AdminService;
import com.maven.tc.service.AdmissionService;
import com.maven.tc.service.ApplicationService;
import com.maven.tc.service.InterviewResultService;
import com.maven.tc.service.NoticeService;
import com.maven.tc.service.SecondInterviewService;
import com.maven.tc.service.StudentService;
import com.maven.tc.service.WalkInInterviewService;
import com.maven.tc.utils.JwtUtils;
import com.maven.tc.utils.Result;
import com.maven.tc.vo.AdminVO;
import com.maven.tc.vo.AdmissionVO;
import com.maven.tc.vo.ApplicationVO;
import com.maven.tc.vo.DirectionStatisticsVO;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.LoginVO;
import com.maven.tc.vo.NoticeVO;
import com.maven.tc.vo.SecondInterviewVO;
import com.maven.tc.vo.StudentVO;
import com.maven.tc.vo.WalkInInterviewVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private SecondInterviewService secondInterviewService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private WalkInInterviewService walkInInterviewService;

    @Autowired
    private AdmissionService admissionService;

    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<LoginVO> login(@Valid @RequestBody AdminLoginDTO adminLoginDTO) {
        AdminVO admin = adminService.login(adminLoginDTO.getUsername(), adminLoginDTO.getPassword());
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }
        String token = JwtUtils.generateToken(admin.getId(), "admin");//使用 JWT 工具类生成访问令牌
        return Result.success("登录成功", new LoginVO(token, admin));//将 Token 和管理员信息封装到 LoginVO 中返回
    }

    @GetMapping("/students")
    @Operation(summary = "查看注册学生")
    public Result<IPage<StudentVO>> getStudents(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(studentService.getStudents(current, size));
    }


    @PutMapping("/student/{id}")
    @Operation(summary = "修改学生信息")
    public Result<String> updateStudent(@PathVariable Long id,
                                        @RequestBody UpdateStudentDTO dto) {
        studentService.updateStudent(id, dto);
        return Result.success("修改成功");
    }

    @DeleteMapping("/student/{id}")
    @Operation(summary = "删除学生")
    public Result<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
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
    public Result<String> addApplication(@RequestBody AdminApplicationDTO dto) {
        applicationService.addApplication(dto);
        return Result.success("添加成功");
    }

    @PutMapping("/application/{id}")
    @Operation(summary = "修改报名信息")
    public Result<String> updateApplication(@PathVariable Long id,
                                            @RequestBody AdminApplicationDTO dto) {
        try {
            applicationService.updateApplication(id, dto);
            return Result.success("修改成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/application/{id}")
    @Operation(summary = "删除报名信息")
    public Result<String> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
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

    @PostMapping("/interviewResult")
    @Operation(summary = "录入面试结果")
    public Result<String> addInterviewResult(@RequestBody AddInterviewResultDTO dto) {
        try {
            interviewResultService.addInterviewResult(dto);
            return Result.success("录入成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/interviewResult/{id}")
    @Operation(summary = "修改一面信息")
    public Result<String> updateInterviewResult(@PathVariable Long id,
                                                @RequestBody UpdateInterviewResultDTO dto) {
        interviewResultService.updateInterviewResult(id, dto);
        return Result.success("修改成功");
    }

    @DeleteMapping("/interviewResult/{id}")
    @Operation(summary = "删除一面信息")
    public Result<String> deleteInterviewResult(@PathVariable Long id) {
        interviewResultService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/secondInterviews")
    @Operation(summary = "查看二面学生")
    public Result<IPage<SecondInterviewVO>> getSecondInterviews(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String direction) {
        return Result.success(secondInterviewService.getSecondInterviews(current, size, direction));
    }

    @PostMapping("/secondInterview")
    @Operation(summary = "添加二面学生")
    public Result<String> addSecondInterview(@RequestBody SecondInterviewDTO dto) {
        try {
            secondInterviewService.addSecondInterview(dto);
            return Result.success("添加成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/secondInterview/batchAdd")
    @Operation(summary = "批量添加一面通过的学生到二面")
    public Result<String> batchAddSecondInterview() {
        try {
            String message = secondInterviewService.batchAddSecondInterview();
            return Result.success(message);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/secondInterview/passedFirst")
    @Operation(summary = "查看一面通过但未参加二面的学生")
    public Result<List<InterviewResultVO>> getPassedFirstNotSecond() {
        return Result.success(secondInterviewService.getPassedFirstNotSecond());
    }

    @PutMapping("/secondInterview/{id}")
    @Operation(summary = "修改二面信息")
    public Result<String> updateSecondInterview(@PathVariable Long id,
                                                @RequestBody UpdateSecondInterviewDTO dto) {
        secondInterviewService.updateSecondInterview(id, dto);
        return Result.success("修改成功");
    }

    @DeleteMapping("/secondInterview/{id}")
    @Operation(summary = "删除二面信息")
    public Result<String> deleteSecondInterview(@PathVariable Long id) {
        secondInterviewService.deleteSecondInterview(id);
        return Result.success("删除成功");
    }

    @GetMapping("/notices")
    @Operation(summary = "查看通知列表")
    public Result<IPage<NoticeVO>> getNotices(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String type) {
        return Result.success(noticeService.getNotices(current, size, type));
    }

    @PostMapping("/notice")
    @Operation(summary = "发布通知")
    public Result<String> addNotice(@RequestBody NoticeDTO dto) {
        noticeService.addNotice(dto);
        return Result.success("发布成功");
    }

    @PutMapping("/notice/{id}")
    @Operation(summary = "修改通知")
    public Result<String> updateNotice(@PathVariable Long id,
                                       @RequestBody NoticeDTO dto) {
        noticeService.updateNotice(id, dto);
        return Result.success("修改成功");
    }

    @DeleteMapping("/notice/{id}")
    @Operation(summary = "删除通知")
    public Result<String> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return Result.success("删除成功");
    }

    @GetMapping("/directionStatistics")
    @Operation(summary = "查看各方向报名和通过人数统计")
    public Result<List<DirectionStatisticsVO>> getDirectionStatistics() {
        return Result.success(applicationService.getDirectionStatistics());
    }

    @GetMapping("/walkInInterviews")
    @Operation(summary = "查看霸面学生")
    public Result<IPage<WalkInInterviewVO>> getWalkInInterviews(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String direction) {
        return Result.success(walkInInterviewService.getWalkInInterviews(current, size, direction));
    }

    @PostMapping("/walkInInterview")
    @Operation(summary = "添加霸面学生")
    public Result<String> addWalkInInterview(@RequestBody WalkInInterviewDTO dto) {
        try {
            walkInInterviewService.addWalkInInterview(dto);//添加霸面学生
            return Result.success("添加成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/walkInInterview/{id}")
    @Operation(summary = "修改霸面信息")
    public Result<String> updateWalkInInterview(@PathVariable Long id,
                                                @RequestBody UpdateWalkInInterviewDTO dto) {
        walkInInterviewService.updateWalkInInterview(id, dto);
        return Result.success("修改成功");
    }

    @DeleteMapping("/walkInInterview/{id}")
    @Operation(summary = "删除霸面信息")
    public Result<String> deleteWalkInInterview(@PathVariable Long id) {
        walkInInterviewService.deleteWalkInInterview(id);
        return Result.success("删除成功");
    }

    @GetMapping("/admissions")
    @Operation(summary = "查看录取人员")
    public Result<IPage<AdmissionVO>> getAdmissions(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String direction) {
        return Result.success(admissionService.getAdmissions(current, size, direction));
    }
}