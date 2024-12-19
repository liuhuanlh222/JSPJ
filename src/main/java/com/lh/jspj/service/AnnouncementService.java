package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.Result;
import com.lh.jspj.entity.Announcement;

/**
 * @author LH
 * @version 1.0
 * @description 公告服务类
 * @date 2024/12/19 09:35
 */
public interface AnnouncementService extends IService<Announcement> {
    Result getAnnouncement(int pageNum, int pageSize);

    Result publishAnnouncement(Announcement announcement);

    Result getNotice();

}
