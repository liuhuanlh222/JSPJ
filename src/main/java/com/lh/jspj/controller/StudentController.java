package com.lh.jspj.controller;

import cn.hutool.core.util.StrUtil;
import com.lh.jspj.dto.AddCourseDTO;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.Evaluation;
import com.lh.jspj.entity.Student;
import com.lh.jspj.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get")
    public Result getStudent() {
        return studentService.get();
    }

    @GetMapping("/getCourse")
    public Result getCourse(int pageNum, int pageSize) {
        return studentService.getCourse(pageNum, pageSize);
    }

    @GetMapping("/info")
    public Result info() {
        return studentService.info();
    }

    @GetMapping("evaluate")
    public Result evaluate(@RequestParam("id") Long id) {
        return studentService.evaluate(id);
    }

    @PostMapping("addEvaluate")
    public Result addEvaluate(@RequestBody Evaluation evaluation) {
        return studentService.addEvaluate(evaluation);
    }

    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody AddCourseDTO addCourseDTO) {
        return studentService.addCourse(addCourseDTO);
    }

    @GetMapping("/teachers")
    public Result teachers() {
        return studentService.teachers();
    }
}
