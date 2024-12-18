package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.dto.UserHolder;
import com.lh.jspj.entity.*;
import com.lh.jspj.mapper.AdminMapper;
import com.lh.jspj.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author LH
 * @version 1.0
 * @description 管理员服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @Resource
    private CourseService courseService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private TeacherCourseServiceImpl teacherCourseService;

    @Resource
    private StudentCourseServiceImpl studentCourseService;

    @Override
    public Result get() {
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @Override
    public Result getTeacher(int pageNum, int pageSize) {
        List<Teacher> teachers = teacherService.list();
        if (teachers.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        page.setRecords(teachers);
        return Result.ok(page.getRecords());
    }

    @Transactional
    @Override
    public Result updateTeacher(Teacher teacher) {
        teacher.setIdentity(2);
        teacherService.updateById(teacher);
        return Result.ok();
    }

    @Override
    public Result deleteTeacherById(Long id) {
        List<TeacherCourse> teacherCourses = teacherCourseService.query().eq("teacher_id", id).list();
        if (!teacherCourses.isEmpty()) {
            return Result.fail("该教师教授有课程，无法删除");
        }
        teacherService.removeById(id);
        return Result.ok();
    }

    @Override
    public Result getStudent(int pageNum, int pageSize) {
        List<Student> students = studentService.list();
        if (students.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        Page<Student> page = new Page<>(pageNum, pageSize);
        page.setRecords(students);
        return Result.ok(page.getRecords());
    }

    @Transactional
    @Override
    public Result updateStudent(Student student) {
        student.setIdentity(3);
        studentService.updateById(student);
        return Result.ok();
    }

    @Override
    public Result deleteStudentById(Long id) {
        List<StudentCourse> studentCourses = studentCourseService.query().eq("student_id", id).list();
        if (!studentCourses.isEmpty()) {
            return Result.fail("该学生学有课程，无法删除");
        }
        studentService.removeById(id);
        return Result.ok();
    }

    @Override
    public Result getCourse(int pageNum, int pageSize) {
        List<Course> courses = courseService.list();
        if (courses.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        for (Course course : courses) {
            Long courseId = course.getId();
            //获取分数
            Score score = scoreService.query().eq("course_id", courseId).one();
            //获取教师姓名
            TeacherCourse teacherCourse = teacherCourseService.query().eq("course_id", courseId).one();
            String teacherName = teacherService.query().eq("id", teacherCourse.getTeacherId()).one().getName();
            course.setScore(score.getScore());
            course.setTeacherName(teacherName);
        }
        Page<Course> page = new Page<>(pageNum, pageSize);
        page.setRecords(courses);
        return Result.ok(page.getRecords());
    }

    @Transactional
    @Override
    public Result updateCourse(Course course) {
        //获取课程id
        Long courseId = course.getId();
        //判断教师是否存在
        String teacherName = course.getTeacherName();
        Teacher teacher = teacherService.query().eq("name", teacherName).one();
        if (teacher == null) {
            return Result.fail("教师不存在");
        }

        //更改课程信息
        Course OldCourse = courseService.query().eq("id", courseId).one();
        OldCourse.setTeacherName(teacherName);
        OldCourse.setCourseName(course.getCourseName());
        OldCourse.setStartTime(course.getStartTime());
        OldCourse.setEndTime(course.getEndTime());
        //课程信息表更新
        courseService.updateById(OldCourse);

        //教师课程信息表更新
        TeacherCourse teacherCourse = teacherCourseService.query().eq("course_id", courseId).one();
        teacherCourse.setTeacherId(teacher.getId());
        teacherCourseService.updateById(teacherCourse);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result deleteCourseById(Long id) {
        //删除学生课程信息表，将学生课程数量减一
        List<StudentCourse> studentCourses = studentCourseService.query().eq("course_id", id).list();
        //一个课程可以对应多个学生
        if (!studentCourses.isEmpty()) {
            for (StudentCourse studentCourse : studentCourses) {
                Long studentId = studentCourse.getStudentId();
                studentCourseService.removeById(studentCourse);
                studentService.update().setSql("course_number = course_number - 1").eq("id", studentId).update();
            }
        }
        //删除教师课表信息表，一个课程对应一个教师
        TeacherCourse teacherCourse = teacherCourseService.query().eq("course_id", id).one();
        teacherCourseService.removeById(teacherCourse);
        //删除课程信息
        courseService.removeById(id);
        return Result.ok();
    }
}
