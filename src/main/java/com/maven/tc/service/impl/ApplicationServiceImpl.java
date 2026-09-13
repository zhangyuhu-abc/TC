package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.dto.AdminApplicationDTO;
import com.maven.tc.dto.ApplicationDTO;
import com.maven.tc.entity.Application;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.entity.SecondInterview;
import com.maven.tc.entity.WalkInInterview;
import com.maven.tc.mapper.ApplicationMapper;
import com.maven.tc.service.ApplicationService;
import com.maven.tc.service.InterviewResultService;
import com.maven.tc.service.SecondInterviewService;
import com.maven.tc.service.WalkInInterviewService;
import com.maven.tc.vo.ApplicationVO;
import com.maven.tc.vo.DirectionStatisticsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private InterviewResultService interviewResultService;

    @Autowired
    private SecondInterviewService secondInterviewService;

    @Autowired
    private WalkInInterviewService walkInInterviewService;

    @Override
    //查看报名学生
    public IPage<ApplicationVO> getApplications(Integer current, Integer size, String direction, String interviewTime) {
        Page<Application> page = new Page<>(current, size);//创建分页实例
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();//创建 LambdaQueryWrapper 实例
        if (StringUtils.hasText(direction)) {
            wrapper.eq(Application::getDirection, direction);//添加面试方向查询条件
        }
        if (StringUtils.hasText(interviewTime)) {
            wrapper.eq(Application::getInterviewTime, interviewTime);//添加面试时间查询条件
        }
        Page<Application> resultPage = this.page(page, wrapper);//根据 LambdaQueryWrapper 分页查询
        return resultPage.convert(application -> {
            ApplicationVO vo = new ApplicationVO();//创建 ApplicationVO 实例
            org.springframework.beans.BeanUtils.copyProperties(application, vo);//将 Application 实体中的数据复制到 ApplicationVO 实例中
            return vo;
        });
    }

    @Override
    //查看各方向报名和通过人数统计
    public List<DirectionStatisticsVO> getDirectionStatistics() {
        LambdaQueryWrapper<Application> applyWrapper = new LambdaQueryWrapper<>();
        applyWrapper.select(Application::getDirection);//查询面试方向
        List<Application> applications = this.list(applyWrapper);
        
        Map<String, Long> applyCountMap = applications.stream()
                .collect(Collectors.groupingBy(
                        Application::getDirection,
                        Collectors.counting()
                ));//统计每个方向的报名人数 用键值对存储
        
        LambdaQueryWrapper<InterviewResult> firstPassWrapper = new LambdaQueryWrapper<>();
        firstPassWrapper.eq(InterviewResult::getStatus, "已通过");
        List<InterviewResult> firstPassedResults = interviewResultService.list(firstPassWrapper);
        
        Map<String, Long> firstPassCountMap = firstPassedResults.stream()
                .collect(Collectors.groupingBy(
                        InterviewResult::getDirection,
                        Collectors.counting()
                ));//查询通过一面的人数
        
        LambdaQueryWrapper<SecondInterview> secondPassWrapper = new LambdaQueryWrapper<>();
        secondPassWrapper.eq(SecondInterview::getStatus, "已通过");
        List<SecondInterview> secondPassedResults = secondInterviewService.list(secondPassWrapper);
        
        Map<String, Long> secondPassCountMap = secondPassedResults.stream()
                .collect(Collectors.groupingBy(
                        SecondInterview::getDirection,
                        Collectors.counting()
                ));//查询通过二面的人数
        
        LambdaQueryWrapper<WalkInInterview> walkInPassWrapper = new LambdaQueryWrapper<>();
        walkInPassWrapper.eq(WalkInInterview::getStatus, "已通过");
        List<WalkInInterview> walkInPassedResults = walkInInterviewService.list(walkInPassWrapper);
        
        Map<String, Long> walkInPassCountMap = walkInPassedResults.stream()
                .collect(Collectors.groupingBy(
                        WalkInInterview::getDirection,
                        Collectors.counting()
                ));//查询通过霸面的人数
        
        Set<String> allDirections = new HashSet<>();
        allDirections.addAll(applyCountMap.keySet());
        allDirections.addAll(firstPassCountMap.keySet());
        allDirections.addAll(secondPassCountMap.keySet());
        allDirections.addAll(walkInPassCountMap.keySet());//合并
        
        List<DirectionStatisticsVO> statistics = new ArrayList<>();
        for (String direction : allDirections) {
            DirectionStatisticsVO vo = new DirectionStatisticsVO();
            vo.setDirection(direction);
            vo.setApplyCount(applyCountMap.getOrDefault(direction, 0L));
            vo.setFirstPassCount(firstPassCountMap.getOrDefault(direction, 0L));
            vo.setSecondPassCount(secondPassCountMap.getOrDefault(direction, 0L));
            vo.setWalkInPassCount(walkInPassCountMap.getOrDefault(direction, 0L));
            statistics.add(vo);
        }
        
        return statistics;
    }

    @Override
    //学生报名
    public void apply(ApplicationDTO dto) {
        Application application = new Application();
        BeanUtils.copyProperties(dto, application);
        this.save(application);
    }

    @Override
    //管理员添加报名学生
    public void addApplication(AdminApplicationDTO dto) {
        Application application = new Application();
        BeanUtils.copyProperties(dto, application);
        this.save(application);
    }

    @Override
    //管理员修改报名学生
    public void updateApplication(Long id, AdminApplicationDTO dto) {
        Application application = this.getById(id);
        if (application == null) {
            throw new RuntimeException("报名信息不存在");
        }
        BeanUtils.copyProperties(dto, application);
        this.updateById(application);
    }

    @Override
    //管理员删除报名学生
    public void deleteApplication(Long id) {
        this.removeById(id);
    }
}