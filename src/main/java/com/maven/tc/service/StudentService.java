package com.maven.tc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.dto.RegisterDTO;
import com.maven.tc.entity.Student;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.StudentVO;

import java.util.Map;

public interface StudentService extends IService<Student> {

    StudentVO register(RegisterDTO dto);

    StudentVO login(String studentId, String password);

    InterviewResultVO getInterviewResult(String studentId);

    Map<String, Object> getDirections();

    Map<String, Object> getLearningPaths();
}