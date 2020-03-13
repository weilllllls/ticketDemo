package com.product.ticket.mapper;
import	java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.product.ticket.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {

    /**
     * 查商票列表
     * @param userId
     * @param giveWay
     * @param lock
     * @return
     */
    public List<Ticket> selectTickets(@Param("userId")Long userId,
                                      @Param("mortgage")String mortgage,
                                      @Param("lock")String lock,
                                      @Param("ticketNum")String ticketNum,
                                      @Param("price")String price,
                                      @Param("startDate")String startDate,
                                      @Param("endDate")String endDate);

    public List<Ticket> selectReceverTickets(@Param("userId")Long userId,
                                      @Param("mortgage")String mortgage,
                                      @Param("lock")String lock,
                                      @Param("ticketNum")String ticketNum,
                                      @Param("price")String price,
                                      @Param("startDate")String startDate,
                                      @Param("endDate")String endDate);

    /**
     * 查未确认列表
     * @param userId
     * @return
     */
    public List<Ticket> selectUnconfirmTickets(@Param("userId")Long userId);
}
