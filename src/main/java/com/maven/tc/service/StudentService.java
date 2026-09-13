package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.dto.RegisterDTO;
import com.maven.tc.dto.UpdateStudentDTO;
import com.maven.tc.entity.Student;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.SecondInterviewVO;
import com.maven.tc.vo.StudentVO;
import com.maven.tc.vo.WalkInInterviewVO;

import java.util.Map;

public interface StudentService extends IService<Student> {

    StudentVO register(RegisterDTO dto);

    StudentVO login(String studentId, String password);

    IPage<StudentVO> getStudents(Integer current, Integer size);

    void updateStudent(Long id, UpdateStudentDTO dto);

    void deleteStudent(Long id);

    InterviewResultVO getInterviewResult(String studentId);

    SecondInterviewVO getSecondInterviewResult(String studentId);

    WalkInInterviewVO getWalkInInterviewResult(String studentId);

    Map<String, Object> getDirections();

    Map<String, Object> getLearningPaths();

    boolean resetPassword(String studentId, String newPassword);

    boolean updatePassword(String studentId, String oldPassword, String newPassword);
}