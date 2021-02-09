package com.utils.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class BeanUtilsTest {

    Person person = new Person(11, "男", "xjb");
    List<Person> list = new ArrayList<>();

    {
        list.add(new Person(11, "男", "xjb"));
        list.add(new Person(11, "男", "md"));
        list.add(new Person(11, "女", "xxx"));
    }

    @Test
    public void to() {
        Student s1 = BeanUtils.to(person, Student.class);
        Student s2 = BeanUtils.toIfPresent(person, Student.class);

        person = null;
        Student s3 = BeanUtils.toIfPresent(person, Student.class);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }

    @Test
    public void listBeanCopy() {

        list.stream()
                .map(BeanUtils.to(Student.class))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        list.add(null);
        list.stream()
                .map(BeanUtils.toIfPresent(Student.class))
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Person {
        protected Integer age;
        protected String sex;
        protected String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Student extends Person {
        private String className;

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", sex='" + sex + '\'' +
                    ", name='" + name + '\'' +
                    ", className='" + className + '\'' +
                    '}';
        }
    }
}
