package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.dto.UserHolder;
import com.lh.jspj.entity.Message;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.mapper.MessageMapper;
import com.lh.jspj.service.MessageService;
import com.lh.jspj.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author LH
 * @version 1.0
 * @description 留言服务实现类
 * @date 2024/12/22 17:03
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private TeacherService teacherService;

    @Override
    public Result getStudentMessage(Long id, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        Page<Message> message = query().eq("student_id", id).eq("status", 1).page(page);
        if (message.getRecords().isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        return Result.ok(message.getRecords());
    }

    @Override
    public Result getTeacherMessage(Long id, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        Page<Message> message = query().eq("teacher_id", id).eq("status", 1).page(page);
        if (message.getRecords().isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        return Result.ok(message.getRecords());
    }

    @Override
    public Result getAdminMessage(int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        Page<Message> message = query().eq("status", 0).page(page);
        if (message.getRecords().isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        return Result.ok(message.getRecords());
    }

    @Override
    public Result deleteMessage(Long id) {
        Message message = query().eq("id", id).one();
        if (message == null) {
            return Result.fail("该留言不存在");
        }
        removeById(message);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result addMessage(Message message) {
        //获取学成信息
        UserDTO student = UserHolder.getUser();
        Long studentId = student.getId();
        String teacherName = message.getTeacherName();
        Teacher teacher = teacherService.query().eq("name", teacherName).one();
        if (teacher == null) {
            return Result.fail("该教师不存在");
        }
        message.setStudentId(studentId);
        message.setStudentName(student.getName());
        message.setTeacherId(teacher.getId());
        message.setStatus(0);
        save(message);
        return Result.ok();
    }

    @Override
    public Result approveMessage(Long id) {
        update().set("status", 1).eq("id", id).update();
        return Result.ok();
    }

    @Override
    public Result rejectMessage(Long id) {
        //直接删除留言
        deleteMessage(id);
        return Result.ok();
    }
}
