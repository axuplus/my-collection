package com.utils.research.mapper;
import com.utils.research.config.SuperMapper;
import com.utils.research.pojo.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface MsgMapper extends SuperMapper<Msg> {

    List<Msg> getList();
}
