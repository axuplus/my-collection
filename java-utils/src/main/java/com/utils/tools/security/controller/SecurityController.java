package com.utils.tools.security.controller;

import com.utils.tools.security.pojo.Company;
import com.utils.tools.security.service.SecurityService;
import com.utils.util.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class SecurityController{

    @Autowired
    private SecurityService securityService;

    @GetMapping("get/companies")
    public Wrapper<List<Company>> getCompanyList(){
       return  securityService.getCompanyList();
    }
}
