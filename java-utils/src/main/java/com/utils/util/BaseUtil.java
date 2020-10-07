package com.utils.util;

import com.aliyun.oss.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Desc:
 * @Author:cyw
 * @CreateTime: 2020/6/11 17:40
 **/
public class BaseUtil {

    protected void thrServiceException(String messsage) {
        throw new ServiceException(messsage);
    }

    protected Boolean isSame(Object obj, Object obj1) {
        return Objects.equals(obj, obj1);
    }

    protected Boolean isNotSame(Object obj, Object obj1) {
        return !isSame(obj, obj1);
    }

    protected Boolean isNull(Object object) {
        return object == null;
    }

    protected Boolean isNotNull(Object object) {
        return object != null;
    }

    protected void isNullThr(Object object,String errMsG) {
        Optional.ofNullable(object)
                .orElseThrow(()->new ServiceException(errMsG));
    }

    protected Boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    protected Boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    protected void isEmptyThr(Collection<?> collection,String errMsG) {
        Optional.ofNullable(collection)
                .filter(this::isNotEmpty)
                .orElseThrow(()->new ServiceException(errMsG));
    }

    protected Boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    protected Boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    protected void isEmptyThr(Map<?, ?> map,String errMsG) {
        Optional.ofNullable(map)
                .filter(this::isNotEmpty)
                .orElseThrow(()->new ServiceException(errMsG));
    }

    protected Boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    protected Boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    protected void isEmptyThr(Object[] objects,String errMsG) {
        Optional.ofNullable(objects)
                .filter(this::isNotEmpty)
                .orElseThrow(()->new ServiceException(errMsG));
    }

    protected Boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    protected Boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    protected Boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    protected Boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    protected void isEmptyThr(String str,String errMsG) {
        Optional.ofNullable(str)
                .filter(this::isNotEmpty)
                .orElseThrow(()->new ServiceException(errMsG));
    }

    protected <T> T selectOne(Collection<T> list) {
        return isEmpty(list) ? null :
                list.stream()
                        .filter(this::isNotNull)
                        .findFirst()
                        .orElse(null);
    }
}

    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    