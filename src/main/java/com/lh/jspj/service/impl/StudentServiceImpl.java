package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Student;
import com.lh.jspj.mapper.StudentMapper;
import com.lh.jspj.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 学生服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
