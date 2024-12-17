package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author LH
 * @version 1.0
 * @description 分数信息
 * @date 2024/12/17 16:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_score")
public class Score {

    private Long id;

    private Long courseId;

    private double score;

}
