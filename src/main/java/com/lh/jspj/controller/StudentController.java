package com.lh.jspj.controller;

import cn.hutool.core.util.StrUtil;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Student;
import com.lh.jspj.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LH
 * @version 1.0
 * @description 学生前端控制器
 * @date 2024/12/19 16:46
 */
@RestController
@RequestMapping("student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
}
