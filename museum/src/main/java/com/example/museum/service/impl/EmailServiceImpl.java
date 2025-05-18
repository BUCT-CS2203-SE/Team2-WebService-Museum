package com.example.museum.service.impl;

import java.time.LocalDateTime;
import java.util.Hashtable;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.museum.config.CodeGenerator;
import com.example.museum.mapper.VerificationCodeMapper;
import com.example.museum.model.VerificationCode;
import com.example.museum.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    //有效时间10分钟
    private static final int EXPIRE_MINUTES = 10;
    @Autowired
    private VerificationCodeMapper vfcm;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String Mailfrom;

    @Override
    public void sendVerificationEmail(String to, String code) {
        try {
            MimeMessage mimeMsg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "UTF-8");

            helper.setFrom(Mailfrom);
            helper.setTo(to);
            helper.setSubject("【海外文物知识服务子系统】邮箱验证码");

            String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                      <meta charset="UTF-8">
                      <style>
                        .container { font-family: Arial, sans-serif; padding: 20px; }
                        .code { display: inline-block; padding: 10px 20px;
                                font-size: 24px; color: #ffffff; background-color: #007bff;
                                border-radius: 4px; margin: 10px 0; }
                        .footer { font-size: 12px; color: #888888; margin-top: 20px; }
                      </style>
                    </head>
                    <body>
                      <div class="container">
                        <h2>验证码通知</h2>
                        <p>您好，您本次操作的邮箱验证码为：</p>
                        <div class="code">
                    """ // 关闭文本块
                    + code // 拼接变量
                    + """
                                </div>
                                <p>此验证码 10 分钟内有效，请勿泄露给他人。</p>
                                <div class="footer">如果这不是您本人操作，请忽略此邮件。</div>
                              </div>
                            </body>
                            </html>
                            """; // 重新打开并关闭文本块
            helper.setText(html, true);
            mailSender.send(mimeMsg);
        } catch (MessagingException e) {
            // 记录日志或抛出自定义异常
            throw new MailSendException("发送验证码邮件失败", e);
        }
    }

    @Override
    public void sendCode(String email) throws Exception{
        String code = CodeGenerator.generate(6);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(EXPIRE_MINUTES);
        VerificationCode vfcd = new VerificationCode(null, email, code, expires, null);
        System.out.println("start Send email");
        try{
            sendVerificationEmail(email, code);
            vfcm.insert(vfcd);
            System.out.println("成功发送验证码: "+code+" To: "+email+" End Time: "+expires);
        }catch(Exception e){throw new Exception(e.getMessage());}
    }

    @Override
    public Boolean verifyCode(String email, String code){
        VerificationCode vfcd = vfcm.findValid(email, code, LocalDateTime.now());
        if (vfcd != null) {
            vfcm.deleteById(vfcd.getId());  // 验证通过后立即删除 
            return true;
        }
        return false;
    }
    @Override
    public Boolean isValidFormat(String email){
        System.out.println("<-- 开始邮箱格式校验 -->");
        EmailValidator validator = EmailValidator.getInstance(false, true);
        boolean first_valid = validator.isValid(email);
        if(!first_valid) return false;
        String domain = email.substring(email.indexOf('@') + 1);
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            Attribute mx = attrs.get("MX");
            if (mx != null && mx.size() > 0) {
                System.out.println("MX True");
                return true;
            }
            System.out.println("MX Error, A Checking");
            // 回退查 A
            attrs = ctx.getAttributes(domain, new String[]{"A"});
            Attribute a = attrs.get("A");
            return a != null && a.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public VerificationCode findUnconfirmedCode(String email){
        return vfcm.findValidByEmail(email,LocalDateTime.now());
    }
}
