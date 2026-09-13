package com.maven.tc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maven.tc.entity.Admission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdmissionMapper extends BaseMapper<Admission> {
}