package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.entity.Admission;
import com.maven.tc.vo.AdmissionVO;

public interface AdmissionService extends IService<Admission> {

    IPage<AdmissionVO> getAdmissions(Integer current, Integer size, String direction);
}