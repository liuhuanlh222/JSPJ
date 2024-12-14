package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Course;
import com.lh.jspj.mapper.CourseMapper;
import com.lh.jspj.service.CourseService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 课程服务实现类
 * @date 2024/12/13 14:43
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
