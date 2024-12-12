package com.lh.jspj.dto;

import lombok.Data;

/**
 * @author LH
 * @version 1.0
 * @description 隐藏用户敏感信息
 * @date 2024/12/10 10:56
 */
@Data
public class UserDTO {

    private Long id;

    private String nickName;

    private int identity;

}
