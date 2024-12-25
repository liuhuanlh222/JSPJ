package com.lh.jspj.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.lh.jspj.config.AliPayConfig;
import com.lh.jspj.entity.Course;
import com.lh.jspj.entity.Order;
import com.lh.jspj.service.CourseService;
import com.lh.jspj.service.OrderService;
import com.lh.jspj.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("alipay")
public class AliPayController {

    // 支付宝沙箱网关地址
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private OrderService ordersService;

    @Resource
    private CourseService courseService;

    @Resource
    private StudentService studentService;

    @GetMapping("/pay")
    public void pay(String orderNo, HttpServletResponse httpResponse) throws Exception {
        // 查询订单信息
        Order order = ordersService.query().eq("order_no", orderNo).one();
        if (order == null) {
            return;
        }
        //查询课程信息
        Course course = courseService.query().eq("id", order.getCourseId()).one();
        //创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        //创建 Request并设置Request参数
        AlipayTradePagePayRequest request = getAlipayTradePagePayRequest(order, course);
        //执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    private AlipayTradePagePayRequest getAlipayTradePagePayRequest(Order order, Course course) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", order.getOrderNo());//我们自己生成的订单编号
        bizContent.set("total_amount", 100);//订单的总金额
        bizContent.set("subject", course.getCourseName());//支付的名称
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");//固定配置
        request.setBizContent(bizContent.toString());
        request.setReturnUrl("http://localhost:8081/student");//支付完成后自动跳转到本地页面的路径
        return request;
    }

    @PostMapping("/notify") // 注意这里必须是POST接口
    public void payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                String tradeNo = params.get("out_trade_no");//订单编号
                String gmtPayment = params.get("gmt_payment");//支付时间
                String alipayTradeNo = params.get("trade_no");//支付宝交易编号
                // 更新订单状态为已支付，设置支付信息
                //设置时间解析格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(gmtPayment, formatter);
                Order order = ordersService.query().eq("order_no", tradeNo).one();
                order.setStatus(1);
                order.setPayTime(dateTime);
                order.setPayNo(alipayTradeNo);
                ordersService.updateById(order);

                //修改数据库其它信息
                studentService.addCourseSuccess(order.getStudentId(), order.getCourseId());

            }
        }
    }
}