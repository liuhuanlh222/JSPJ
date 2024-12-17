package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 学生与课程
 * @date 2024/12/16 15:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student_course")
public class StudentCourse {

    private Long id;

    private Long studentId;

    private Long courseId;

    private LocalDateTime createTime;
}
