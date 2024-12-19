package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Student;
import com.lh.jspj.mapper.StudentMapper;
import com.lh.jspj.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 学生服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Result addStudent(Student student) {
        //判断账号是否唯一
        Student student1 = query().eq("username", student.getUsername()).one();
        if (student1 != null) {
            return Result.fail("该账户已经存在");
        }
        student.setIdentity(3);
        student.setCourseNumber(0);
        save(student);
        return Result.ok();
    }
}
