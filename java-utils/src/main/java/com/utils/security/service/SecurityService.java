package com.utils.security.service;

import com.utils.security.pojo.Company;
import com.utils.util.wrapper.Wrapper;

import java.util.List;

public interface SecurityService {

    Wrapper<List<Company>> getCompanyList();

}
