package com.utils.tools.research;

import com.utils.tools.research.mapper.MsgMapper;
import com.utils.tools.research.pojo.Msg;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class Research {

    @Autowired
    private MsgMapper msgMapper;

    @Test
    public void peekTest() {
        List<Msg> msgs = msgMapper.selectList(null);
        Optional<Msg> any = msgs.stream().filter(p -> p.getType() != 0).findAny();
        System.out.println("any = " + any);
    }

    @Test
    @Order
    public void test() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        map.put("key", 123);
    }

    @Test
    public void whenCreatesEmptyOptional_thenCorrect() {
        User user = new User();
        user.setName("Joma");
        user.setAge(21);
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("Emma");
        user1.setAge(29);
        users.add(user);
        users.add(user1);
        List<User> collect = users.stream().filter(u -> u.getName() != null).map(u -> new User(user.getName(), 23)).collect(Collectors.toList());
        System.out.println("users = " + collect);
    }

    @Test
    public void testLock(){

    }


    @Data
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class User {
        protected String name;
        protected Integer age;

        public User(String name, Integer i) {

        }
    }
}
