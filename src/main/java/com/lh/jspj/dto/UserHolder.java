package com.lh.jspj.dto;

/**
 * @author LH
 * @version 1.0
 * @description 保存与获取用户
 * @date 2024/12/10 11:47
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user) {
        tl.set(user);
    }

    public static UserDTO getUser() {
        return tl.get();
    }

    public static void removeUser() {
        tl.remove();
    }
}
