package com.utils.research.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.utils.research.mapper.MsgMapper;
import com.utils.research.pojo.Msg;
import com.utils.research.service.MsgService;
import com.utils.util.dto.LoginAuthDto;
import com.utils.util.support.BaseController;
import com.utils.util.wrapper.BaseQueryDto;
import com.utils.util.wrapper.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private MsgMapper msgMapper;

    @Autowired
    private MsgService msgService;


    @PostMapping("/update")
    public Boolean update() {
        Msg msg = new Msg();
        msg.setId(45662456666L);
        msg.setContent("哈哈哈哈哈dididi");
        msg.setCreateTime(5886622123L);
        msg.setTitle("标题");
        msg.setType(100);
        return msgService.saveOrUpdate(msg);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        int delete = msgMapper.delete(new QueryWrapper<Msg>().lambda().eq(Msg::getId, id));
        System.out.println("delete = " + delete);
        return true;
    }

    @GetMapping("/list")
    public PageWrapper<List<Msg>> getList(BaseQueryDto baseQueryDto) {
        return msgService.getListByPage(baseQueryDto);
    }

    @GetMapping
    public Boolean test() {
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        return true;
    }

}
