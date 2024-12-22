package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author LH
 * @version 1.0
 * @description 留言信息
 * @date 2024/12/22 16:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_message")
public class Message {

    private Long id;

    private Long teacherId;

    private String teacherName;

    private Long studentId;

    private String studentName;

    private String content;
}
