package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Admin;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.Student;
import com.lh.jspj.entity.Teacher;

/**
 * @author LH
 * @version 1.0
 * @description 管理员服务类
 * @date 2024/12/12 15:24
 */
public interface AdminService extends IService<Admin> {
    Result get();

    Result getTeacher(int pageNum, int pageSize);

    Result updateTeacher(Teacher teacher);

    Result deleteTeacherById(Long id);

    Result getStudent(int pageNum, int pageSize);

    Result updateStudent(Student student);

    Result deleteStudentById(Long id);

    Result getCourse(int pageNum, int pageSize);

    Result updateCourse(Course course);
}
