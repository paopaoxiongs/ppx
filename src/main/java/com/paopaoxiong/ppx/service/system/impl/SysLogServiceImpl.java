package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.SysLogMapper;
import com.paopaoxiong.ppx.model.system.SysLog;
import com.paopaoxiong.ppx.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void add(SysLog sysLog) {
        sysLogMapper.add(sysLog);
    }
}
