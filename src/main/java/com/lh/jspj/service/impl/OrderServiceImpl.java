package com.lh.jspj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.jspj.entity.Order;
import com.lh.jspj.mapper.OrderMapper;
import com.lh.jspj.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author LH
 * @version 1.0
 * @description 订单服务实现类
 * @date 2024/12/24 22:35
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
