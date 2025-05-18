package com.example.museum.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 自定义日志输出
 */
public class DbLogger {
    public static final Logger DB_LOGGER = LoggerFactory.getLogger("DB_LOGGER");

    public static void log(String username, String sql) {
        DB_LOGGER.info("User: [{}], Time: [{}], SQL: {}", username,
                java.time.LocalDateTime.now(), sql);
    }
}
