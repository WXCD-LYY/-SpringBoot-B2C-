package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/3/8/008 16:55
 **/
public class TokenRequestInterceptor implements RequestInterceptor {


    /**
     * Feign执行之前，进行拦截
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        /**
         *
         */


        // 生成admin令牌
        String token = AdminToken.adminToken();
        template.header("Authorization", "bearer " + token);


    }
}
