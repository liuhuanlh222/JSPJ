package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 学生信息
 * @date 2024/12/12 14:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_student")
public class Student {
    //主键
    private Long id;

    private String username;

    private String password;

    //身份
    private int identity;

    //昵称
    private String name;

    //选课数量
    private Integer courseNumber;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
