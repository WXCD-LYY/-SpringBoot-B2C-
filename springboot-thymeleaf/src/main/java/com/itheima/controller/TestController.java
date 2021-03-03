package com.itheima.controller;

import com.itheima.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Company: CUG
 * @Description: Thymeleaf模板引擎
 * @Author: LiYangyong
 * @Date: 2021/2/28/028 15:18
 **/
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "hello")
    public String hello(Model model) {
        model.addAttribute("message", "hello thymeleaf!");

        // 创建按一个List<User>
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"张三","深圳"));
        users.add(new User(2,"李四","北京"));
        users.add(new User(3,"王五","武汉"));
        model.addAttribute("users",users);


        //Map定义
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("No","123");
        dataMap.put("address","深圳");
        model.addAttribute("dataMap",dataMap);

        return "demo1";
    }
}
