package com.changgou.search.controller;

import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/2/28/028 17:03
 **/
@Controller
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false) Map<String, String> searchMap, Model model) throws Exception{
        //调用changgou-com.changgou.content.service-search微服务
        Map<String, Object> resultMap = skuFeign.search(searchMap);
        model.addAttribute("result", resultMap);


        // 计算分页
        Page<SkuInfo> skuInfo = new Page<SkuInfo>(
                Long.parseLong(resultMap.get("total").toString()),
                Integer.parseInt(resultMap.get("pageNumber").toString()), // 需要+1  但是SkuServiceImpl有问题
                Integer.parseInt(resultMap.get("pageSize").toString()));

        model.addAttribute("pageInfo", skuInfo);

        // 讲条件存储用于页面回显数据
        model.addAttribute("searchMap", searchMap);


        // 获取上次请求的地址
        String[] urls = url(searchMap);
        // 2个url，url:不带排序  url:带排序
        model.addAttribute("url", urls[0]);
        model.addAttribute("sortUrl", urls[1]);
        return "search";
    }

    /**
     * 获取用户每次请求的地质
     * 页面需要在爱喝茶请求的地址上面添加额外的搜索条件
     * http://localhost:18086/search/list
     * http://localhost:18086/search/list?keywords=华为
     * http://localhost:18086/search/list?keywords=华为&。。。。
     */

    public String[] url(Map<String, String> searchMap) {
        String url = "/search/list"; // 初始化地址
        String sorturl = "/search/list"; // 排序地址
        if (searchMap != null && searchMap.size() > 0) {
            url += "?";
            sorturl += "?";
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                // key是搜索条件对象
                String key = entry.getKey();

                // 跳过分页参数
                if (key.equalsIgnoreCase("pageNum")) {
                    continue;
                }

                // value搜索的值
                String value = entry.getValue();
                url += key + "=" + value + "&";

                // 排序参数，跳过
                if (key.equalsIgnoreCase("sortField") || key.equalsIgnoreCase("sortRule")) {
                    continue;
                }
                sorturl += key + "=" + value + "&";


            }

            // 去掉最后一个&
            url = url.substring(0, url.length() - 1);
            sorturl = sorturl.substring(0, sorturl.length() - 1);
        }
        return new String[]{url, sorturl};
    }

}
