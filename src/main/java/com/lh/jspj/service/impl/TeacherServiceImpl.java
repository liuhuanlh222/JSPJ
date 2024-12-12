package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.mapper.TeacherMapper;
import com.lh.jspj.service.TeacherService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 教师服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
