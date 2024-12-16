package com.lh.jspj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.RedisData;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.TeacherCourse;
import com.lh.jspj.mapper.CourseMapper;
import com.lh.jspj.service.CourseService;
import com.lh.jspj.service.TeacherCourseService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.lh.jspj.utils.RedisConstant.CACHE_COURSE_KEY;

/**
 * @author LH
 * @version 1.0
 * @description 课程服务实现类
 * @date 2024/12/13 14:43
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TeacherCourseService teacherCourseService;

    @Override
    public Result getCourseByTeacherId(Long teacherId) {
        //查询连接表
        List<TeacherCourse> teacherCourses = teacherCourseService.query().eq("teacher_id", teacherId).list();
        //获取courseId
        List<Long> courseIds = teacherCourses.stream().map(TeacherCourse::getCourseId).toList();
        //查询课程
        List<Course> courses = listByIds(courseIds);
        if (courses.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        return Result.ok(courses);
    }

    //课程逻辑信息添加
    public void savaCourse(Long id, Long expireSeconds) throws InterruptedException {
        //查询课程数据
        Course course = getById(id);
        Thread.sleep(200);
        //封装逻辑过期时间
        RedisData redisData = new RedisData();
        redisData.setData(course);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
        //写入redis
        stringRedisTemplate.opsForValue().set(CACHE_COURSE_KEY + id, JSONUtil.toJsonStr(redisData));
    }
}
