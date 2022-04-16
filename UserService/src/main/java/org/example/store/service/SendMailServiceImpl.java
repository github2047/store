package org.example.store.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements SendMailService{
    @Value("${spring.mail.from}")
    private String from;
    private JavaMailSender javaMailSender;
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendEmail(String to, String code){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);//谁发的
//        message.setTo(to); //谁要接收
//        message.setSubject("Store注册验证码");//邮件标题
//        message.setText("尊敬的:"+to+"\n您正在Store注册账号，当前的验证码为:<span style=\"font-size:20px;color:red\">" + code+"</span>,此验证码15分钟类有效，请及时填写注册，若不是本人操作，请忽略。");
        String html="尊敬的:"+to+"<br/>您正在Store注册账号，当前的验证码为:<br/><span style=\"font-size:20px;color:red\">" + code+"</span><br/><span style=\"color:#01d2ff\">此验证码15分钟类有效</span>，请及时填写注册，若不是本人操作，请忽略并且删除它。";
        try {
            // 创建一个有内容类型的消息
            MimeMessage message = javaMailSender.createMimeMessage();
            // 消息内容设置助手
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 消息内容
            helper.setText(html, true); // 设置为html类型
            // 消息主题
            helper.setSubject("Store注册验证码");
            // 接收者
            helper.setTo(to);
            // 发送者
            helper.setFrom(from);
            // 发送邮件
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
