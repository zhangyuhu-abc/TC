package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.dto.RegisterDTO;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.entity.Student;
import com.maven.tc.mapper.InterviewResultMapper;
import com.maven.tc.mapper.StudentMapper;
import com.maven.tc.service.StudentService;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private InterviewResultMapper interviewResultMapper;

    @Override
    public StudentVO register(RegisterDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }
        if (dto.getStudentId() == null || dto.getStudentId().isEmpty()) {
            throw new RuntimeException("学号不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
            throw new RuntimeException("密码长度不能小于6位");
        }
        if (dto.getMajor() == null || dto.getMajor().isEmpty()) {
            throw new RuntimeException("专业不能为空");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new RuntimeException("邮箱不能为空");
        }
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new RuntimeException("手机号不能为空");
        }
        if (!dto.getPhone().matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("手机号格式不正确");
        }

        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId, dto.getStudentId());
        if (this.count(wrapper) > 0) {
            throw new RuntimeException("该学号已注册");
        }

        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        this.save(student);

        StudentVO vo = new StudentVO();
        BeanUtils.copyProperties(student, vo);
        return vo;
    }

    @Override
    public StudentVO login(String studentId, String password) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId, studentId)
                .eq(Student::getPassword, password);
        Student student = this.getOne(wrapper);
        if (student == null) {
            return null;
        }
        StudentVO vo = new StudentVO();
        BeanUtils.copyProperties(student, vo);
        return vo;
    }

    @Override
    public InterviewResultVO getInterviewResult(String studentId) {
        LambdaQueryWrapper<InterviewResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewResult::getStudentId, studentId);
        InterviewResult result = interviewResultMapper.selectOne(wrapper);
        if (result == null) {
            return null;
        }
        InterviewResultVO vo = new InterviewResultVO();
        BeanUtils.copyProperties(result, vo);
        return vo;
    }

    @Override
    public Map<String, Object> getDirections() {
        Map<String, Object> data = new HashMap<>();

        Map<String, String> embedded = new HashMap<>();
        embedded.put("name", "嵌入式开发");
        embedded.put("description", "嵌入式开发主要是为特定硬件平台开发专用软件，通常用于微控制器、单片机、IoT设备等。");
        embedded.put("tech", "C、C++、RTOS、单片机、ARM");

        Map<String, String> fullstack = new HashMap<>();
        fullstack.put("name", "全栈开发");
        fullstack.put("description", "全栈开发同时涵盖前端与后端开发技术，能够独立完成整套软件系统的开发工作。既掌握HTML、CSS、JavaScript等前端技术，又精通服务器端逻辑开发、数据库设计与管理等后端核心工作。");
        fullstack.put("tech", "HTML、CSS、JavaScript、Vue、React、SpringBoot、MySQL");

        Map<String, String> android = new HashMap<>();
        android.put("name", "安卓开发");
        android.put("description", "安卓开发专注于为Android操作系统平台创建移动应用，覆盖智能手机、平板等设备。");
        android.put("tech", "Java、Kotlin、Android SDK、SQLite、Room");

        data.put("嵌入式开发", embedded);
        data.put("全栈开发", fullstack);
        data.put("安卓开发", android);
        return data;
    }

    @Override
    public Map<String, Object> getLearningPaths() {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> embedded = new HashMap<>();
        embedded.put("title", "嵌入式开发（Embedded Systems）");
        embedded.put("steps", new String[]{
                "基础知识：学习嵌入式系统的基本原理、硬件结构和编程语言（如C、C++）",
                "硬件学习：掌握单片机、传感器、嵌入式系统的设计和开发",
                "RTOS学习：熟悉实时操作系统（RTOS）的使用和应用",
                "项目实践：参与嵌入式项目，如智能家居、物联网设备等"
        });

        Map<String, Object> fullstack = new HashMap<>();
        fullstack.put("title", "全栈开发（Full Stack Development）");
        fullstack.put("steps", new String[]{
                "前后端基础：掌握HTML、CSS、JavaScript及Java、Go等前后端核心编程语言",
                "框架与开发：熟练使用Vue、React、SpringBoot等主流前后端开发框架",
                "数据与架构：掌握MySQL、MongoDB数据库，了解微服务与RESTful API设计",
                "工程与优化：熟悉Git版本管理、多端适配、系统性能优化与安全防护"
        });

        Map<String, Object> android = new HashMap<>();
        android.put("title", "安卓开发（Android Development）");
        android.put("steps", new String[]{
                "编程语言：掌握Java或Kotlin编程语言",
                "Android SDK：学习Android开发工具和框架",
                "布局与界面：了解Android布局和用户界面设计",
                "数据存储：学习SQLite、Room等Android数据存储技术",
                "发布与优化：学习应用发布流程和性能优化技巧"
        });

        data.put("嵌入式开发", embedded);
        data.put("全栈开发", fullstack);
        data.put("安卓开发", android);
        return data;
    }
}