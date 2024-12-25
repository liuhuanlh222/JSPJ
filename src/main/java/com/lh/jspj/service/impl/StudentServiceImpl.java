package com.lh.jspj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.AddCourseDTO;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.dto.UserHolder;
import com.lh.jspj.entity.*;
import com.lh.jspj.mapper.StudentMapper;
import com.lh.jspj.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author LH
 * @version 1.0
 * @description 学生服务实现类
 * @date 2024/12/12 15:49
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource
    private CourseService courseService;

    @Resource
    private StudentCourseService studentCourseService;

    @Resource
    private TeacherCourseService teacherCourseService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private EvaluationService evaluationService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private OrderService orderService;


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

    @Override
    public Result get() {
        UserDTO student = UserHolder.getUser();
        return Result.ok(student);
    }

    @Override
    public Result getCourse(int pageNum, int pageSize) {
        Long studentId = UserHolder.getUser().getId();
        List<StudentCourse> studentCourses = studentCourseService.query().eq("student_id", studentId).list();
        if (studentCourses.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        List<Long> courseId = studentCourses.stream().map(StudentCourse::getCourseId).toList();
        List<Course> courses = courseService.listByIds(courseId);
        for (Course course : courses) {
            //获取评教状态
            StudentCourse studentCourse = studentCourseService.query().eq("student_id", studentId).eq("course_id", course.getId()).one();
            course.setStatus(studentCourse.getStatus());
            //获得教师姓名
            TeacherCourse teacherCourse = teacherCourseService.query().eq("course_id", course.getId()).one();
            String name = teacherService.query().eq("id", teacherCourse.getTeacherId()).one().getName();
            course.setTeacherName(name);
        }
        Page<Course> page = new Page<>(pageNum, pageSize);
        page.setRecords(courses);
        return Result.ok(page.getRecords());
    }

    @Override
    public Result info() {
        UserDTO user = UserHolder.getUser();
        Student student = query().eq("id", user.getId()).one();
        return Result.ok(student);
    }

    @Override
    public Result evaluate(Long id) {
        LocalDateTime now = LocalDateTime.now();
        Course course = courseService.query().eq("id", id).one();
        if (now.isBefore(course.getStartTime()) || now.isAfter(course.getEndTime())) {
            return Result.fail("不在评教时间范围内");
        }
        return Result.ok();
    }

    @Transactional
    @Override
    public Result addEvaluate(Evaluation evaluation) {
        //获取学生id
        Long studentId = UserHolder.getUser().getId();
        //获取课程id
        Long courseId = evaluation.getCourseId();
        evaluation.setStudentId(studentId);
        //计算得分
        double score = (evaluation.getIndexOne() + evaluation.getIndexTwo() + evaluation.getIndexThree() + evaluation.getIndexFour() + evaluation.getIndexFive()) / 5;
        evaluation.setScore(score);
        //保存评价记录
        evaluationService.save(evaluation);

        //修改学生课程信息表评价状态
        studentCourseService.update().setSql("status = 1").eq("student_id", studentId).eq("course_id", courseId).update();

        //修改课程信息表
        courseService.update().setSql("evaluation = evaluation + 1").setSql("un_evaluation = un_evaluation - 1").eq("id", courseId).update();

        //修改分数表
        List<Evaluation> evaluations = evaluationService.query().eq("course_id", courseId).list();
        double totalScore = 0;
        for (Evaluation evaluation1 : evaluations) {
            totalScore += evaluation1.getScore();
        }
        totalScore /= evaluations.size();
        scoreService.update().set("score", totalScore).eq("course_id", courseId).update();

        return Result.ok();
    }

    @Transactional
    @Override
    public Result addCourse(AddCourseDTO addCourseDTO) {
        Long studentId = UserHolder.getUser().getId();
        //获取学生和老师姓名
        String courseName = addCourseDTO.getCourseName();
        String teacherName = addCourseDTO.getTeacherName();
        //获取教师id
        Teacher teacher = teacherService.query().eq("name", teacherName).one();
        if (teacher == null) {
            return Result.fail("教师不存在");
        }
        Long teacherId = teacher.getId();
        //判断是否有该课程以及老师
        List<Course> courses = courseService.query().eq("course_name", courseName).list();
        if (courses.isEmpty()) {
            return Result.fail("课程不存在");
        }
        boolean ifFlag = false;
        TeacherCourse teacherCourse = new TeacherCourse();
        for (Course course : courses) {
            teacherCourse = teacherCourseService.query().eq("teacher_id", teacherId).eq("course_id", course.getId()).one();
            if (teacherCourse != null) {
                ifFlag = true;
                break;
            }
        }
        if (!ifFlag) {
            return Result.fail("教师课程信息有误");
        }
        //查看学生课程信息
        StudentCourse studentCourse = studentCourseService.query().eq("student_id", studentId).eq("course_id", teacherCourse.getCourseId()).one();
        if (studentCourse != null) {
            return Result.fail("该课程已经选择");
        }
        //创建订单
        Order order = new Order();
        String orderNo = UUID.randomUUID().toString();
        order.setCourseId(teacherCourse.getCourseId());
        order.setStudentId(studentId);
        order.setCreateTime(LocalDateTime.now());
        order.setOrderNo(orderNo);
        order.setStatus(0);
        orderService.save(order);
        return Result.ok(orderNo);
    }

    @Transactional
    @Override
    public void addCourseSuccess(Long studentId, Long courseId) {
        //修改学生课程表
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        studentCourse.setStatus(0);
        studentCourseService.save(studentCourse);
        //增加课程选人数量
        courseService.update().setSql("number = number + 1").setSql("un_evaluation = un_evaluation + 1").eq("id", courseId).update();
        //增加学生选课数量
        update().setSql("course_number = course_number + 1").eq("id", studentId).update();
    }

    @Override
    public Result teachers() {
        //获取id
        Long studentId = UserHolder.getUser().getId();
        List<StudentCourse> studentCourses = studentCourseService.query().eq("student_id", studentId).list();
        if (studentCourses.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        List<String> teachersName = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            //获取课程id
            Long courseId = studentCourse.getCourseId();
            //根据课程id查询教师
            TeacherCourse teacherCourse = teacherCourseService.query().eq("course_id", courseId).one();
            Teacher teacher = teacherService.query().eq("id", teacherCourse.getTeacherId()).one();
            teachersName.add(teacher.getName());
        }
        return Result.ok(teachersName);
    }
}
