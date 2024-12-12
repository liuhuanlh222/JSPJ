package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Admin;
import com.lh.jspj.mapper.AdminMapper;
import com.lh.jspj.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 管理员服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
