package com.utils.tools.research.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.utils.tools.research.pojo.Msg;
import com.utils.util.wrapper.BaseQueryDto;
import com.utils.util.wrapper.PageWrapper;

import java.util.List;

public interface MsgService extends IService<Msg> {

    PageWrapper<List<Msg>> getListByPage(BaseQueryDto baseQueryDto);
}
