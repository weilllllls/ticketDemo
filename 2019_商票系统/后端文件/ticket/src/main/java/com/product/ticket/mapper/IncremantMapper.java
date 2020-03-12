package com.product.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.product.ticket.entity.Increment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncremantMapper extends BaseMapper<Increment> {
}
