package com.utils.tools.upload.mapper;


import com.utils.tools.research.config.BaseMapper;
import com.utils.tools.upload.pojo.SysFileDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysFileMapper extends BaseMapper<SysFileDomain> {

}
