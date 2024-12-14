package com.lh.jspj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.dto.LoginDTO;
import com.lh.jspj.dto.Result;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.entity.Admin;
import com.lh.jspj.entity.Student;
import com.lh.jspj.entity.Teacher;
import com.lh.jspj.mapper.UserDTOMapper;
import com.lh.jspj.service.AdminService;
import com.lh.jspj.service.StudentService;
import com.lh.jspj.service.TeacherService;
import com.lh.jspj.service.UserDTOService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.lh.jspj.utils.RedisConstant.LOGIN_USER_KEY;
import static com.lh.jspj.utils.RedisConstant.LOGIN_USER_TTL;

/**
 * @author LH
 * @version 1.0
 * @description 用户服务实现类
 * @date 2024/12/09 15:50
 */
@Service
public class UserDTOServiceImpl extends ServiceImpl<UserDTOMapper, UserDTO> implements UserDTOService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AdminService adminService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public Result login(LoginDTO loginDTO) {
        //校验用户名和密码
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        //获取tokenKey
        String tokenKey = LOGIN_USER_KEY + username;
//        //判断用户是否已经登录
//        Map<Object, Object> isUser = stringRedisTemplate.opsForHash().entries(tokenKey);
//        if (!isUser.isEmpty()) {
//            return Result.fail("该用户已登录");
//        }
        //根据用户名查询用户
        UserDTO userDTO = findUserDTO(username, password);
        //判断用户是否存在
        if (userDTO == null) {
            return Result.fail("用户名或密码错误");
        }
        //查询用户身份
        int identity = userDTO.getIdentity();
        Long userId = userDTO.getId();
        //将user对象转为HashMap进行存储
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        //保存用户信息到redis中
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        //设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        //5.返回信息
        Map<String, Object> map = new HashMap<>();
        map.put("token", username);
        map.put("identity", identity);
        map.put("id", userId);
        return Result.ok(map);
    }

    public UserDTO findUserDTO(String username, String password) {
        //创建三个并行任务
        CompletableFuture<Admin> adminCompletableFuture = CompletableFuture.supplyAsync(() ->
                adminService.query().eq("username", username).one(), executorService);

        CompletableFuture<Teacher> teacherCompletableFuture = CompletableFuture.supplyAsync(() ->
                teacherService.query().eq("username", username).one(), executorService);

        CompletableFuture<Student> studentCompletableFuture = CompletableFuture.supplyAsync(() ->
                studentService.query().eq("username", username).one(), executorService);

        //等待所有任务执行完成
        CompletableFuture.allOf(adminCompletableFuture, teacherCompletableFuture, studentCompletableFuture);
        //创建UserDTO对象
        UserDTO userDTO = new UserDTO();

        try {
            //管理员
            Admin admin = adminCompletableFuture.get();
            if (admin != null && admin.getPassword().equals(password)) {
                BeanUtil.copyProperties(admin, userDTO);
                return userDTO;
            }
            //教师
            Teacher teacher = teacherCompletableFuture.get();
            if (teacher != null && teacher.getPassword().equals(password)) {
                BeanUtil.copyProperties(teacher, userDTO);
                return userDTO;
            }
            //学生
            Student student = studentCompletableFuture.get();
            if (student != null && student.getPassword().equals(password)) {
                BeanUtil.copyProperties(student, userDTO);
                return userDTO;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
