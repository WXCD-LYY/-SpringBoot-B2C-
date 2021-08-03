package com.changgou.filter;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/3/4/004 13:49
 **/

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 实现全局过滤
 * 实现用户权限校验
 */
public class AuthorizeFilter implements GlobalFilter, Ordered {
    // 令牌的名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    /***
     * 全局过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 登录或者不需要权限的直接放行



        // 获取用户令牌信息 从三部分中获取
        // 1 头文件中
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        // boolean true：令牌在头文件中 false：令牌不在头文件中->将令牌封装到头文件中，再传递给其他微服务
        boolean hasToken = true;
        // 2 参数中
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasToken = false;
        }
        // 3 Cookie中
        if (StringUtils.isEmpty(token)) {
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (httpCookie != null) {
                token = httpCookie.getValue();
            }
        }

        // 如果没有令牌，则拦截
//        if (StringUtils.isEmpty(token)) {
//            // 设置没有权限的状态码 401
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            // 响应空数据
//            return response.setComplete();
//        }


        // 令牌判断是否为空,如果不为空，将令牌放到头文件中 放行

        // 如果有令牌，则校验令牌是否有效
        /*try {
//            JwtUtil.parseJWT(token);
//            return chain.filter(exchange);

        } catch (Exception e) {
            // 无效则拦截
            // 设置没有权限的状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }*/

        // 如果令牌为空，则允许访问，直接拦截
        if (StringUtils.isEmpty(token)) {
            // 设置没有权限的状态码 401
            // 设置没有权限的状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        } else {
            if (!hasToken) {
                // 判断是否有bearer前缀，如果没有则添加前缀bearer
                if (!token.startsWith("bearer ") && !token.startsWith("Bearer ")) {
                    token = "bearer " + token;
                }
                // 将令牌封装到头文件中
                request.mutate().header(AUTHORIZE_TOKEN, token);
            }
        }

        // 有效放行
        return chain.filter(exchange);
    }

    /**
     * 排序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
