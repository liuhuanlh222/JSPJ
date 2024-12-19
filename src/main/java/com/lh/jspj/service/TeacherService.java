package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Teacher;
import org.apache.ibatis.executor.ResultExtractor;

/**
 * @author LH
 * @version 1.0
 * @description 教师服务类
 * @date 2024/12/12 15:24
 */
public interface TeacherService extends IService<Teacher> {
    Result get();

    Result info();

    Result getCourse(int pageNum, int pageSize);

    Result addTeacher(Teacher teacher);
}
