package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.Score;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.entity.TeacherCourse;
import com.lh.jspj.mapper.CourseMapper;
import com.lh.jspj.service.CourseService;
import com.lh.jspj.service.ScoreService;
import com.lh.jspj.service.TeacherCourseService;
import com.lh.jspj.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * @author LH
 * @version 1.0
 * @description 课程服务实现类
 * @date 2024/12/13 14:43
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Lazy
    @Resource
    private TeacherService teacherService;

    @Resource
    private TeacherCourseService teacherCourseService;

    @Resource
    private ScoreService scoreService;

    @Transactional
    @Override
    public Result addCourse(Course course) {
        String teacherName = course.getTeacherName();
        Teacher teacher = teacherService.query().eq("name", teacherName).one();
        if (teacher == null) {
            return Result.fail("该教师不存在");
        }
        //增加课程信息
        course.setNumber(0);
        course.setEvaluation(0);
        course.setUnEvaluation(0);
        save(course);
        //增加分数信息
        Score score = new Score();
        score.setCourseId(course.getId());
        score.setScore(0.00);
        scoreService.save(score);
        //将教师课程增加
        teacherService.update().setSql("course_number = course_number + 1").eq("name", teacherName).update();
        //增加教师课程信息表
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCourseId(course.getId());
        teacherCourse.setTeacherId(teacher.getId());
        teacherCourseService.save(teacherCourse);

        return Result.ok();
    }
}
