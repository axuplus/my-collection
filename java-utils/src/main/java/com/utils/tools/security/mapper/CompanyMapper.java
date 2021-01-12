package com.utils.tools.security.mapper;

import com.utils.tools.research.config.SuperMapper;
import com.utils.tools.security.pojo.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyMapper extends SuperMapper<Company> {
}
