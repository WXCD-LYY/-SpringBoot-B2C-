package com.changgou.goods.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

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
         *获取用户的令牌
         * 将令牌再封装到头文件中
         */

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        /**
         * 获取请求头中的数据
         * 获取所有头的名字
         */
        Enumeration<String> headerName = requestAttributes.getRequest().getHeaderNames();
        while (headerName.hasMoreElements()) {
            // 请求头的key
            String headerKey = headerName.nextElement();
            // 获取请求头的值
            String headerValue = requestAttributes.getRequest().getHeader(headerKey);
            System.out.println(headerKey + ":" + headerValue);

            // 将请求头信息封装到头中，使用Feign调用的时候，会传递给下一个微服务

            template.header(headerKey, headerValue);
        }


    }
}
