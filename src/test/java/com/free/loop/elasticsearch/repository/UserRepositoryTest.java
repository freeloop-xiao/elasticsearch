package com.free.loop.elasticsearch.repository;

import com.free.loop.elasticsearch.entity.User;
import com.free.loop.elasticsearch.reposity.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author free loop
 * @version 1.0
 * @since 2020/9/10 20:52
 */
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() {

        List<String> roles = new ArrayList<>();
        roles.add("super");
        roles.add("admin");
        roles.add("user");
        long start = System.currentTimeMillis();

        for (long i = 1000; i < 2000; i++) {
            User user = new User();
            user.setId(i);
            user.setBirthday("2020-09-16");
            user.setNickName("hello");
            user.setRoles(roles);
            user.setUserName("free loop " + i);
            user.setImages("http://www.baidu.com");
            userRepository.save(user);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }


    @Test
    public void saveList() {

        List<String> roles = new ArrayList<>();
        roles.add("super");
        roles.add("admin");
        roles.add("user");
        List<User> users = new ArrayList<>();
        long start = System.currentTimeMillis();

        for (long i = 5000000; i < 10000000; i++) {
            User user = new User();
            user.setId(i);
            user.setBirthday("2020-09-16");
            user.setNickName("hello");
            user.setRoles(roles);
            user.setUserName("free loop " + i);
            user.setImages("http://www.baidu.com");
            users.add(user);
            if (i % 10000 == 0){
                userRepository.saveAll(users);
                System.out.println("=======================" + i);
                users = new ArrayList<>();
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }
}
