package com.utils.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BeanUtils extends org.springframework.beans.BeanUtils {
    public static <T> T copyPropertiesChaining(Object source, T target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <T> T copyPropertiesChaining(Object source, Supplier<T> supplier) {
        return copyPropertiesChaining(source, supplier.get());
    }

    /**
     * 接受一个输入对象，一个指定类，输出一个指定类的类对象，并将输入对象同名字段复制到类对象中
     *
     * @param source      输入对象
     * @param outPutClass 指定类，此类需有公共无参构造
     * @param <T>         输入对象类型
     * @param <U>         指定类
     * @return 指定类的类对象
     */
    public static <T, U> U to(T source, Class<U> outPutClass) {
        try {
            return copyPropertiesChaining(source, outPutClass.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, U> U toIfPresent(T source, Class<U> target) {
        return null == source ? null : to(source, target);
    }

    /**
     * 接受一个指定类，输出一个"接受一个输入对象，输出一个指定类对象，并将输入对象同名字段复制到类对象中"的 Function 对象
     *
     * @param outPutClass 输出对象的类，此类需有公共无参构造
     * @param <T>         输入对象类型
     * @param <U>         Function 对象的泛型
     * @return "接受一个输入对象，输出一个指定类对象，并将输入对象同名字段复制到类对象中"的 Function 对象
     */
    public static <T, U> Function<T, U> to(Class<U> outPutClass) {
        return t -> to(t, outPutClass);
    }

    public static <T, U> Function<T, U> toIfPresent(Class<U> outPutClass) {
        return t -> toIfPresent(t,outPutClass);
    }

    /**
     * 忽略null
     */
    public static <T> T copyPropertiesIgnoreNull(Object source, T target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        return target;
    }

    public static String[] getNullPropertyNames(Object source) {
        if (source == null) {
            return new String[0];
        }

        BeanWrapper src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
