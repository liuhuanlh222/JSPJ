package com.lh.jspj.controller;

import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Announcement;
import com.lh.jspj.service.AnnouncementService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author LH
 * @version 1.0
 * @description 公告信息前端管理器
 * @date 2024/12/19 09:40
 */
@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    @GetMapping("/getAnnouncement")
    public Result getAnnouncement(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return announcementService.getAnnouncement(pageNum, pageSize);
    }

    @PostMapping("/publish")
    public Result publishAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.publishAnnouncement(announcement);
    }

    @GetMapping("/getNotice")
    public Result getNotice() {
        return announcementService.getNotice();
    }
}
