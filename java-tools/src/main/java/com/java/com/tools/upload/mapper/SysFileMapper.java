package com.java.com.tools.upload.mapper;


import com.java.com.tools.upload.pojo.SysFileDomain;
import com.utils.research.config.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysFileMapper extends BaseMapper<SysFileDomain> {

}
