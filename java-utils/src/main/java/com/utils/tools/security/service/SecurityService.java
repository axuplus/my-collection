package com.utils.tools.security.service;

import com.utils.tools.security.pojo.Company;
import com.utils.util.wrapper.Wrapper;

import java.util.List;

public interface SecurityService {

    Wrapper<List<Company>> getCompanyList();

}
