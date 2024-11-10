package com.huang.controller;

import com.huang.common.lang.Result;
import com.huang.entity.User;
import com.huang.vo.EmailDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
@Api("邮箱接口操作")
@RestController
@RequestMapping("/mail")
public class MailController extends BaseController{

    @Resource
    JavaMailSenderImpl mailSender;


    @ApiOperation("购买成功发送接口")
    @GetMapping("/purchase")
    public Result purchaseSuccessful(Principal principal){

        User user = userService.getByName(principal.getName());
        String email = user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("解忧软件平台官方感谢信");
        message.setText("亲爱的用户您好，感谢您对我们产品的支持与信赖。您的问题和建议我们会及时整理反馈上去，让我们努力为您排忧解难，授人玫瑰手有余香，您的好评是对我们最大的支持与鼓励，也将是我们不断前进的动力！ 谢谢您的光顾，希望与您有更多的合作！再次非常感谢，祝你生活愉快万事如意，期待您的再次光临！");
        message.setTo(email);
        message.setFrom("你的邮箱");

        mailSender.send(message);
        return Result.succ("");
    }

    @ApiOperation("未支付订单发送接口")
    @GetMapping("/order")
    public Result purchase(Principal principal) throws InterruptedException {

        User user = userService.getByName(principal.getName());
        String email = user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("解忧软件平台官方");
        message.setText("亲，打扰了哦！这边看到您有未支付的订单，是软件性价比不高还是软件不喜欢呢？还希望亲多原谅^_^相信我们一直都在努力！祝您交易愉快！");
        message.setTo(email);
        message.setFrom("你的邮箱");

        //休眠3秒
        TimeUnit.SECONDS.sleep(3);
        mailSender.send(message);
        return Result.succ("");
    }

    @ApiOperation("发送邮箱接口")
    @PostMapping("/send")
    public Result sendMail(@RequestBody EmailDto emailDto){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getText());
        message.setTo(emailDto.getToEmail());
        message.setFrom("你的邮箱");

        mailSender.send(message);
        return Result.succ("");
    }

}
