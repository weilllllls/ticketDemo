package com.product.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.product.ticket.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
