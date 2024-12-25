package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LH
 * @version 1.0
 * @description 订单信息
 * @date 2024/12/24 22:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
public class Order {

    private Long id;

    private Long courseId;

    private Long studentId;

    private LocalDateTime createTime;

    private String orderNo;

    private String payNo;

    private LocalDateTime payTime;

    private int status;
}
