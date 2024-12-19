package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.dto.UserHolder;
import com.lh.jspj.entity.Announcement;
import com.lh.jspj.mapper.AnnouncementMapper;
import com.lh.jspj.service.AdminService;
import com.lh.jspj.service.AnnouncementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author LH
 * @version 1.0
 * @description 公告服务实现类
 * @date 2024/12/19 09:36
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Override
    public Result getAnnouncement(int pageNum, int pageSize) {
        //获取管理员身份
        UserDTO user = UserHolder.getUser();
        String adminName = user.getName();
        //根据管理员姓名查询公告
        List<Announcement> announcements = query().eq("publisher", adminName).list();
        if (announcements.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        return Result.ok(announcements);
    }

    @Transactional
    @Override
    public Result publishAnnouncement(Announcement announcement) {
        //获取管理员
        UserDTO user = UserHolder.getUser();
        //封装内容信息
        String content = announcement.getContent();
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setContent(content);
        newAnnouncement.setPublisher(user.getName());
        newAnnouncement.setPublishTime(LocalDateTime.now());
        save(newAnnouncement);
        return Result.ok();
    }

    @Override
    public Result getNotice() {
        List<Announcement> announcements = query().list();
        if (announcements.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        return Result.ok(announcements);
    }
}
