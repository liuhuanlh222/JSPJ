package com.lh.jspj.controller;

import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author LH
 * @version 1.0
 * @description 教师前端控制器
 * @date 2024/12/13 15:49
 */
@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("/get")
    public Result get() {
        return teacherService.get();
    }

    @GetMapping("/info")
    public Result info() {
        return teacherService.info();
    }

    @GetMapping("/getCourse")
    public Result getCourse(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return teacherService.getCourse(pageNum, pageSize);
    }

    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

}
