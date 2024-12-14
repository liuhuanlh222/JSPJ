package com.lh.jspj.controller;

import com.lh.jspj.dto.Result;
import com.lh.jspj.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
