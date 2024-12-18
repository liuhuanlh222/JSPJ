package com.lh.jspj.controller;

import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.Student;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author LH
 * @version 1.0
 * @description 管理员前端管理器
 * @date 2024/12/17 15:02
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/get")
    public Result get() {
        return adminService.get();
    }

    @GetMapping("/getTeacher")
    public Result getTeacher(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return adminService.getTeacher(pageNum, pageSize);
    }

    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        return adminService.updateTeacher(teacher);
    }

    @GetMapping("/deleteTeacherById")
    public Result deleteTeacherById(@RequestParam("id") Long id) {
        return adminService.deleteTeacherById(id);
    }

    @GetMapping("/getStudent")
    public Result getStudent(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return adminService.getStudent(pageNum, pageSize);
    }

    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student) {
        return adminService.updateStudent(student);
    }

    @GetMapping("/deleteStudentById")
    public Result deleteStudentById(@RequestParam("id") Long id) {
        return adminService.deleteStudentById(id);
    }

    @GetMapping("/getCourse")
    public Result getCourse(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return adminService.getCourse(pageNum, pageSize);
    }

    @PostMapping("/updateCourse")
    public Result updateCourse(@RequestBody Course course) {
        return adminService.updateCourse(course);
    }

    @GetMapping("/deleteCourseById")
    public Result deleteCourseById(@RequestParam("id") Long id) {
        return adminService.deleteCourseById(id);
    }

}
