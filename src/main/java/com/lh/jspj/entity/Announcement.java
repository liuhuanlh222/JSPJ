package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lh.jspj.dto.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 公告信息
 * @date 2024/12/19 09:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_announcement")
public class Announcement {

    private Long id;

    private String content;

    private String publisher;

    private LocalDateTime publishTime;
}
