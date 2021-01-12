package com.utils.tools.research.mapper;
import com.utils.tools.research.config.SuperMapper;
import com.utils.tools.research.pojo.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface MsgMapper extends SuperMapper<Msg> {

    List<Msg> getList();
}
