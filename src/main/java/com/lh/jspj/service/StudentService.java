package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.AddCourseDTO;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Evaluation;
import com.lh.jspj.entity.Student;

/**
 * @author LH
 * @version 1.0
 * @description 学生服务类
 * @date 2024/12/12 15:24
 */
public interface StudentService extends IService<Student> {
    Result addStudent(Student student);

    Result get();

    Result getCourse(int pageNum, int pageSize);

    Result info();

    Result evaluate(Long id);

    Result addEvaluate(Evaluation evaluation);

    Result addCourse(AddCourseDTO addCourseDTO);

    void addCourseSuccess(Long studentId, Long courseId);

    Result teachers();
}
