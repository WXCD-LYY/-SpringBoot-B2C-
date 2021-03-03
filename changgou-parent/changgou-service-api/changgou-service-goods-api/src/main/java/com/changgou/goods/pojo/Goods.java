package com.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/2/23/023 22:50
 **/

/***
 * 商品信息组合信息
 * List<Sku>
 * Spu
 */
public class Goods implements Serializable {

    // Spu信息
    private  Spu spu;

    // Sku集合信息
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
