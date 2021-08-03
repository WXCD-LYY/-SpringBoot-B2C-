package com.changgou.search.service;

import java.util.Map;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/2/27/027 11:07
 **/
public interface SkuService {

    /**
     * 条件搜索
     * @param searchMap
     * @return Map
     */
    Map<String, Object> search(Map<String, String> searchMap) throws Exception;


    /**
     * 导入数据到索引库中
     */

    void importData();


    /***
     * 导入SKU数据
     */
    void importSku();
}
