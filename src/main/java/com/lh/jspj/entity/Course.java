package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 课程信息
 * @date 2024/12/13 14:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_admin")
public class Course {
    private Long id;

    private String courseName;

    private int teacherId;

    private String course_status;

    private LocalDateTime createTime;

    private LocalDateTime endTime;
}
