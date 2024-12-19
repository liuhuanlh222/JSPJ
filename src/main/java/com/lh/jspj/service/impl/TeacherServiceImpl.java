package com.lh.jspj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.dto.UserHolder;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.entity.TeacherCourse;
import com.lh.jspj.mapper.TeacherMapper;
import com.lh.jspj.service.CourseService;
import com.lh.jspj.service.TeacherCourseService;
import com.lh.jspj.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LH
 * @version 1.0
 * @description 教师服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private TeacherCourseService teacherCourseService;

    @Resource
    private CourseService courseService;

    @Override
    public Result get() {
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @Override
    public Result info() {
        //获取用户
        UserDTO user = UserHolder.getUser();
        //查询教师的详细信息
        Teacher teacher = query().eq("id", user.getId()).one();
        return Result.ok(teacher);
    }

    @Override
    public Result getCourse(int pageNum, int pageSize) {
        UserDTO user = UserHolder.getUser();
        //教师对应id
        Long userId = user.getId();
        List<TeacherCourse> teacherCourses = teacherCourseService.query().eq("teacher_id", userId).list();
        if (teacherCourses.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        List<Long> courseIds = teacherCourses.stream().map(TeacherCourse::getCourseId).toList();
        List<Course> courses = courseService.listByIds(courseIds);
        Page<Course> page = new Page<>(pageNum, pageSize);
        page.setRecords(courses);
        return Result.ok(page.getRecords());
    }

    @Override
    public Result addTeacher(Teacher teacher) {
        Teacher teacher1 = query().eq("username", teacher.getUsername()).one();
        if (teacher1 != null) {
            return Result.fail("该账户已存在");
        }
        teacher.setIdentity(2);
        teacher.setCourseNumber(0);
        save(teacher);
        return Result.ok(teacher);
    }
}
