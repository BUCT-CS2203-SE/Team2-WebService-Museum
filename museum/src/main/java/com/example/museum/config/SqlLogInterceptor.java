package com.example.museum.config;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * 拦截器获取SQL语句详情
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        // ─── 跳过第 1 次路由拦截，只在真正的 PreparedStatementHandler 上打日志 ───
        if (handler instanceof RoutingStatementHandler) {
            return invocation.proceed();
        }

        BoundSql boundSql = handler.getBoundSql();
        String rawSql = boundSql.getSql();

        // 填充参数到 SQL 中
        String completeSql = buildSqlWithParams(rawSql, boundSql);

        // 获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "anonymous";

        // 写入日志
        String compressedSql = compressSql(completeSql);
        DbLogger.log(username, compressedSql);

        return invocation.proceed();
    }

    private String buildSqlWithParams(String sql, BoundSql boundSql) {
        List<org.apache.ibatis.mapping.ParameterMapping> paramMappings = boundSql.getParameterMappings();
        Object paramObject = boundSql.getParameterObject();

        if (paramMappings == null || paramMappings.isEmpty() || paramObject == null) {
            return sql;
        }

        MetaObject metaObject = SystemMetaObject.forObject(paramObject);
        StringBuilder finalSql = new StringBuilder();
        int paramIndex = 0;

        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '?') {
                if (paramIndex < paramMappings.size()) {
                    org.apache.ibatis.mapping.ParameterMapping paramMapping = paramMappings.get(paramIndex++);
                    String propertyName = paramMapping.getProperty();
                    Object value;

                    if (metaObject.hasGetter(propertyName)) {
                        value = metaObject.getValue(propertyName);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else {
                        value = "UNKNOWN";
                    }

                    String valueStr = formatValue(value);
                    finalSql.append(valueStr);
                } else {
                    finalSql.append('?');
                }
            } else {
                finalSql.append(sql.charAt(i));
            }
        }

        return finalSql.toString();
    }

    private String formatValue(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof String || obj instanceof java.util.Date || obj instanceof java.time.LocalDateTime) {
            return "'" + obj.toString().replace("'", "''") + "'";
        }
        return obj.toString();
    }

    private String compressSql(String sql) {
        return sql
                .replaceAll("[\\s]+", " ") // 把所有空格、换行、制表符合并成一个空格
                .replaceAll("\\s*,\\s*", ", ") // 规范逗号后空格
                .trim();
    }

    @Override
    public Object plugin(Object target) {
        // 避免重复 wrap：只拦截 StatementHandler 实例
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }


    @Override
    public void setProperties(Properties properties) {}
}
