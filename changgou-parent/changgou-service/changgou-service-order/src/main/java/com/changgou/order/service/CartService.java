package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/3/7/007 22:21
 **/
public interface CartService {

    /**
     * 加入购物车
     */
    void add(Integer num, Long id, String username);


    /**
     * 购物车集合查询
     * @param username 用户名
     */
    List<OrderItem> list(String username);

}
