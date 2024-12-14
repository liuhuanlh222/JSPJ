package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 管理员信息
 * @date 2024/12/12 14:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_admin")
public class Admin {
    //主键
    private Long id;

    private String username;

    private String password;

    //身份
    private int identity;

    //昵称
    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}