package com.lh.jspj.controller;

import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Message;
import com.lh.jspj.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author LH
 * @version 1.0
 * @description 留言前端接口
 * @date 2024/12/22 17:03
 */
@RestController
@RequestMapping("message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("/getStudentMessage")
    public Result getStudentMessage(@RequestParam("id") Long id, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return messageService.getStudentMessage(id, pageNum, pageSize);
    }

    @GetMapping("/getTeacherMessage")
    public Result getTeacherMessage(@RequestParam("id") Long id, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return messageService.getTeacherMessage(id, pageNum, pageSize);
    }

    @GetMapping("/deleteMessage")
    public Result deleteMessage(@RequestParam("id") Long id) {
        return messageService.deleteMessage(id);
    }

    @PostMapping("/addMessage")
    public Result addMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }
}
