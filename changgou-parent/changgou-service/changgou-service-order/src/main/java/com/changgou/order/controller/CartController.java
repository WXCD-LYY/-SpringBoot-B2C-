package com.changgou.order.controller;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/3/7/007 22:20
 **/

import com.changgou.order.config.TokenDecode;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 购物车操作
 */
@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;





    /**
     * 加入购物车
     * 1.加入购物车数量
     * 2.商品ID
     */
    @GetMapping(value = "/add")
    public Result add(Integer num, Long id) {
/*OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String token = details.getTokenValue();*/
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        // 获取用户登录名
        cartService.add(num, id, username);
        return new Result(true, StatusCode.OK, "加入购物车成功！");
    }


    /**
     * 购物车列表
     */
    @GetMapping(value = "/list")
    public Result<List<OrderItem>> list() {


        // 获取用户登录名
        String username = "szitheima";
        // 查询购物车列表
        List<OrderItem> orderItems = cartService.list(username);
        return new Result<List<OrderItem>>(true, StatusCode.OK, "购物车列表查询成功", orderItems);
    }
}
