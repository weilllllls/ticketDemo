package com.product.ticket.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.product.ticket.entity.Increment;
import com.product.ticket.mapper.IncremantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 自动生成随机票据号码工具类 前6位为随机数 后6位为上一张商票后6位+1
 */
@Service
public class IncrementNumUtil {

    @Autowired
    IncremantMapper incremantMapper;

    /**
     * 获取单号
     * @return
     */
    public String getIncrementNum(){
        Increment increment = new Increment();
        Integer integer = incremantMapper.selectCount(new QueryWrapper<Increment>());
        integer+=1;
        increment.setNum(Long.valueOf(integer.toString()));
        System.out.println(increment.getNum());
        int insert = incremantMapper.insert(increment);
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++)
        {
            result+=random.nextInt(10);
        }
        System.out.println(result);
        return result+""+increment.getNum();
    }
}
