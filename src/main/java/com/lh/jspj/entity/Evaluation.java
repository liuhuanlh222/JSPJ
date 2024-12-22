package com.lh.jspj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author LH
 * @version 1.0
 * @description 评教信息
 * @date 2024/12/22 17:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_evaluation")
public class Evaluation {

    private Long id;

    private Long courseId;

    private Long studentId;

    private double indexOne;

    private double indexTwo;

    private double indexThree;

    private double indexFour;

    private double indexFive;

    private double score;
}
