package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.entity.Admission;
import com.maven.tc.mapper.AdmissionMapper;
import com.maven.tc.service.AdmissionService;
import com.maven.tc.vo.AdmissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdmissionServiceImpl extends ServiceImpl<AdmissionMapper, Admission> implements AdmissionService {

    @Override
    //管理员查看录取信息
    public IPage<AdmissionVO> getAdmissions(Integer current, Integer size, String direction) {
        Page<Admission> page = new Page<>(current, size);
        LambdaQueryWrapper<Admission> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(direction)) {
            wrapper.eq(Admission::getDirection, direction);
        }
        wrapper.orderByDesc(Admission::getId);
        Page<Admission> resultPage = this.page(page, wrapper);
        return resultPage.convert(admission -> {
            AdmissionVO vo = new AdmissionVO();
            BeanUtils.copyProperties(admission, vo);
            return vo;
        });
    }
}