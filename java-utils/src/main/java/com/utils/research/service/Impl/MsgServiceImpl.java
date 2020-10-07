package com.utils.research.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.utils.research.mapper.MsgMapper;
import com.utils.research.pojo.Msg;
import com.utils.research.service.MsgService;
import com.utils.util.wrapper.BaseQueryDto;
import com.utils.util.wrapper.PageUtil;
import com.utils.util.wrapper.PageWrapMapper;
import com.utils.util.wrapper.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {

    @Autowired
    private MsgMapper msgMapper;

    @Override
    public PageWrapper<List<Msg>> getListByPage(BaseQueryDto baseQueryDto) {
        Page page = PageHelper.startPage(baseQueryDto.getPageNum(), baseQueryDto.getPageSize());
        List<Msg> list = msgMapper.getList();
        Long total = page.getTotal();
        return PageWrapMapper.wrap(list, new PageUtil(total.intValue(), baseQueryDto.getPageNum(), baseQueryDto.getPageSize()));
    }
}
//docker run -p 56379:56379 --name redis -v /usr/local/redis/conf/redis.conf:/etc/redis/redis.conf  -v /usr/local/redis/data:/data -d redis:5.0.5 redis-server /etc/redis/redis.conf --appendonly yes

