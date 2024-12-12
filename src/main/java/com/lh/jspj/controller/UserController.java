package com.lh.jspj.controller;

import com.lh.jspj.dto.LoginDTO;
import com.lh.jspj.dto.Result;
import com.lh.jspj.service.UserDTOService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LH
 * @version 1.0
 * @description 用户前端控制器
 * @date 2024/12/09 15:58
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserDTOService UserDTOService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        return UserDTOService.login(loginDTO);
    }
}
