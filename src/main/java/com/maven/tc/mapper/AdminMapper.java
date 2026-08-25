package com.maven.tc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maven.tc.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}