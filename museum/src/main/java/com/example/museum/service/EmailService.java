package com.example.museum.service;

import com.example.museum.model.VerificationCode;

/**邮件服务 */
public interface EmailService {
    /**
     * 发送邮件(服务中调用)
     * @param to 接收人邮箱地址
     * @param code 验证码
     */
    void sendVerificationEmail(String to, String code);
    /**
     * 发送邮件服务(正式场景调用)
     * @param email 收信人邮箱地址
     */
    void sendCode(String email) throws Exception;
    /**
     * 校验提交的验证码,校验成功删除记录
     * @param email 邮箱地址
     * @param code 验证码
     * @return
     */
    Boolean verifyCode(String email, String code);
    /**
     * 判断一个邮件地址是否正确(包含正则匹配、DNS MX记录检查)
     * @param email
     * @return
     */
    Boolean isValidFormat(String email);
    /**
     * 查找截止当前，邮箱是否有未被验证的验证码
     * @param email 邮箱地址
     * @return 第一个未被验证的验证码实体类
     */
    VerificationCode findUnconfirmedCode(String email);
}
