package com.lh.jspj.controller;

import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Course;
import com.lh.jspj.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LH
 * @version 1.0
 * @description 课程前端控制器
 * @date 2024/12/16 16:07
 */
@RestController
@RequestMapping("course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping("/getCourseByTeacherId")
    public Result getCourseByTeacherId(@RequestParam("teacherId") Long teacherId) {
        return courseService.getCourseByTeacherId(teacherId);
    }
}
