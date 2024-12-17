package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Score;
import com.lh.jspj.mapper.ScoreMapper;
import com.lh.jspj.service.ScoreService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 分数服务实现类
 * @date 2024/12/17 16:56
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
}
