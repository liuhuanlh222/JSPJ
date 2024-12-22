package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Message;

/**
 * @author LH
 * @version 1.0
 * @description 留言服务类
 * @date 2024/12/22 17:02
 */
public interface MessageService extends IService<Message> {
    Result getStudentMessage(Long id, int pageNum, int pageSize);

    Result getTeacherMessage(Long id, int pageNum, int pageSize);

    Result deleteMessage(Long id);

    Result addMessage(Message message);
}
