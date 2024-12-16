package com.lh.jspj.dto;

import com.lh.jspj.entity.Course;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 逻辑过期实现缓存击穿，本项目中应用在管理员查询学生，教师，课程信息界面
 * @date 2024/12/16 15:32
 */
@Data
public class RedisData {
    //逻辑过期时间
    private LocalDateTime expireTime;
    //基本信息
    private Object data;
}
