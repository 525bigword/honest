package com.xr.run.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysPostMapper;
import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPostService;
import org.springframework.stereotype.Service;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper,SysPost> implements SysPostService {

}
