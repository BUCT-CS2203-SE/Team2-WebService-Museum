package com.example.museum.config;

import java.util.UUID;

/**验证码生成器 */
public class CodeGenerator {
    /** 默认返回 32 字节的十六进制随机字符串 */
    public static String generate() {
        return UUID.randomUUID()
                   .toString()
                   .replace("-", "");
    }

    /** 若需要指定长度，可截取或拼接多次调用 */
    public static String generate(int length) {
        String uuid = generate();
        if (length <= uuid.length()) {
            return uuid.substring(0, length);
        }
        // 拼接若干 UUID 再截断
        StringBuilder sb = new StringBuilder(uuid);
        while (sb.length() < length) {
            sb.append(generate());
        }
        return sb.substring(0, length);
    }
}
