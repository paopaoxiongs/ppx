package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.SysLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogMapper {

    public void add(SysLog sysLog);
}
