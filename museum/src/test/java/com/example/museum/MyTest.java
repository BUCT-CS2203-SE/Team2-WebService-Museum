package com.example.museum;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.museum.dto.CommentDTO;
import com.example.museum.dto.RelicSearchDTO;
import com.example.museum.mapper.AppUserMapper;
import com.example.museum.mapper.ArtMapper;
import com.example.museum.mapper.CommentsMapper;
import com.example.museum.mapper.RelicSearchMapper;
import com.example.museum.mapper.VerificationCodeMapper;
import com.example.museum.model.AppUser;
import com.example.museum.model.Art;
import com.example.museum.model.VerificationCode;
import com.example.museum.service.EmailService;
import com.example.museum.service.MyUserService;
import com.example.museum.vo.DetailVO;
import com.example.museum.vo.RelicItemVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class MyTest {
    @Autowired
    private MyUserService myce;
    @Autowired
    private DataSource dataSource;
    
    
    @Test
    void dataSource() throws SQLException{
        System.out.println(dataSource.getConnection());
    }

    @Test
    void Te1(){
        ArrayList<AppUser> mylist = new ArrayList<>(myce.getAllUser());
        for(AppUser i : mylist){
            System.out.println(i);
        }
        System.out.println("Id is "+myce.getIdByAccount("test"));
    }

    // @Autowired
    // private ArtMapper artmap;
    // @Test
    // void te2() {
    //     ArrayList<Map<String, Object>> li = new ArrayList<>(artmap.findTimelineData());
    //     System.out.println("ALL Size : " + li.size());
    //     for (int i = 0; i < 10; i++) {
    //         for (Map.Entry<String, Object> entry : li.get(i).entrySet()) {
    //             String key = entry.getKey();
    //             String value = String.valueOf(entry.getValue());
    //             System.out.println(key + ": " + value);
    //         }
    //     }
    // }
    
    //以下测试选做，都能使用
    // @Autowired
    // private AppUserMapper apmr;
    // @Test
    // void Test1(){
    //     AppUser user = new AppUser();
    //     user.setAccount("test1");
    //     user.setPassword("123");
    //     user.setEmail("123@12qq.com");
    //     int x = apmr.addOneUser(user);
    //     System.out.println(x);
    // }
    // @Test
    // void test2(){
    //     // myce.deleteById(6L);
    //     AppUser user = myce.findByAccount("test");
    //     user.setPassword(new BCryptPasswordEncoder().encode("123"));
    //     user.setEmail("3309296020@qq.com");
    //     myce.updateOneUser(user);
    //     System.out.println(user);
    // }

    // @Autowired
    // private VerificationCodeMapper vfcm;
    // @Autowired
    // private EmailService emce;
    // @Test
    // void email_test() throws Exception{
    //     emce.sendCode("3309296020@qq.com");
    //     // Boolean ans = emce.verifyCode("3309296020@qq.com", "cc8317");
    //     // System.out.println(ans);
    //     System.out.println("Email Test Over");        
    // }

    @Autowired
    private RelicSearchMapper rsmp;

    @Test
    void asd(){
        // DetailVO ans = rsmp.getDetailInfo(2L);
        // System.out.println("Get Info: "+ans);
        // ObjectMapper mapper = new ObjectMapper();
        // Map<String,Object> map = mapper.convertValue(ans, new TypeReference<>(){});
        // System.out.println(map);
        ArrayList<Object> li = new ArrayList<>(rsmp.getUserHistory("test",null,null));
        for(Object i : li){System.out.println(i.toString());}
        // Boolean an = cmmp.delByCommentsId(1L);
        // System.out.println(an);
        // rsmp.addHistoryRelic(1L, 1L);
    }

    @Autowired
    private CommentsMapper cmmp;
    @Test
    void asdq(){
        System.out.println("ANS: "+cmmp.getCommentByRelicId(2L));
        // CommentDTO dto = new CommentDTO(1L, "test", "Nice!", LocalDateTime.now());
        // Boolean an = cmmp.addComments(dto);
        // System.out.println("Ans: "+an);
    }
}   
