package com.lh.jspj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.jspj.dto.LoginDTO;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;

/**
 * @author LH
 * @version 1.0
 * @description 用户服务类
 * @date 2024/12/09 15:49
 */
public interface UserDTOService extends IService<UserDTO> {
    Result login(LoginDTO loginDTO);
}
