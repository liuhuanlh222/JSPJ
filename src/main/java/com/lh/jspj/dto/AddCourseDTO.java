package com.lh.jspj.dto;

import lombok.Data;

/**
 * @author LH
 * @version 1.0
 * @description 增加课程实体
 * @date 2024/12/24 14:28
 */
@Data
public class AddCourseDTO {

    private String teacherName;

    private String courseName;
}
