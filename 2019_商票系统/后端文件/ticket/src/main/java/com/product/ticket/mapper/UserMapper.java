package com.product.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.product.ticket.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
