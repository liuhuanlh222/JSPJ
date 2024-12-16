package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Course;

/**
 * @author LH
 * @version 1.0
 * @description 课程服务类
 * @date 2024/12/13 14:42
 */
public interface CourseService extends IService<Course> {
    Result getCourseByTeacherId(Long teacherId);
}
