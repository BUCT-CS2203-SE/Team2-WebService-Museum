package com.example.museum.mapper;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.museum.model.VerificationCode;

@Mapper
public interface VerificationCodeMapper {

    /**
     * 保存验证码，自动记录当前时间，ID自增
     * @param vc
     * @return
     */
    @Insert("""
      INSERT INTO verification_code(email, code, expires_at)
      VALUES(#{email}, #{code}, #{expiresAt})
      """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VerificationCode vc);

    /**
     * 查询到目前为止仍有效的验证码，之间无时间差，具体查找匹配验证码
     * @param email
     * @param code
     * @param now
     * @return 单条记录
     */
    @Select("""
      SELECT id, email, code, expires_at AS expiresAt, created_at AS createdAt
      FROM verification_code
      WHERE email = #{email}
        AND code = #{code}
        AND expires_at > #{now}
      """)
    VerificationCode findValid(
      @Param("email") String email,
      @Param("code") String code,
      @Param("now") LocalDateTime now
    );
    /**
     * 查询某邮箱第一条到现在还有效的验证码
     * @param email 邮箱
     * @param now 时间（现在）
     * @return 验证码实体类
     */
    @Select("""
      SELECT id, email, code, expires_at AS expiresAt, created_at AS createdAt
      FROM verification_code
      WHERE email = #{email}
        AND expires_at > #{now}
      """)
    VerificationCode findValidByEmail(
      @Param("email") String email,
      @Param("now") LocalDateTime now
    );

    @Delete("DELETE FROM verification_code WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
}