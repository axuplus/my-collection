package com.utils.security.mapper;

import com.utils.research.config.SuperMapper;
import com.utils.security.pojo.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyMapper extends SuperMapper<Company> {
}
