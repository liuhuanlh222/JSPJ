package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.StudentCourse;
import com.lh.jspj.mapper.StudentCourseMapper;
import com.lh.jspj.service.StudentCourseService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description StudentCourse服务实现类
 * @date 2024/12/17 21:33
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {
}
