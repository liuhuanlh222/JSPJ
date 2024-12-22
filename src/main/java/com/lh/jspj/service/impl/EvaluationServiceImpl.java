package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Evaluation;
import com.lh.jspj.mapper.EvaluationMapper;
import com.lh.jspj.service.EvaluationService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 评教服务实现类
 * @date 2024/12/22 17:28
 */
@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {
}
