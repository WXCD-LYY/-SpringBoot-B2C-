package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/3/7/007 21:11
 **/
@FeignClient(value = "user")
@RequestMapping(value = "/user")
public interface UserFeign {

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
     @GetMapping("/load/{id}")
     Result<User> findById(@PathVariable String id);
}
