package com.utils.security.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.utils.security.mapper.CompanyMapper;
import com.utils.security.pojo.Company;
import com.utils.security.service.SecurityService;
import com.utils.util.wrapper.WrapMapper;
import com.utils.util.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityServiceImpl extends ServiceImpl<CompanyMapper, Company> implements SecurityService {


    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Wrapper<List<Company>> getCompanyList() {
        List<Company> companies = companyMapper.selectList(new QueryWrapper<>(null));
        return WrapMapper.ok(companies);
    }
}
