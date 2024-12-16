package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 教师与课程
 * @date 2024/12/16 15:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("teacher_course")
public class TeacherCourse {

    private Long id;

    private Long teacherId;

    private Long courseId;

    private LocalDateTime createTime;
}
