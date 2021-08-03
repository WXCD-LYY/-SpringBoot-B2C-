package com.changgou.token;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company: CUG
 * @Description: 令牌的创建和解析
 * @Author: LiYangyong
 * @Date: 2021/3/5/005 14:29
 **/

public class CreatJwtTestDemo {

    /**
     * 创建令牌
     */
    @Test
    public void testCreatToken() {
        // 加载证书 读取类路径中的文件
        ClassPathResource resource = new ClassPathResource("changgou02.jks");

        // 读取证书数据
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "changgou02".toCharArray());
        // 获取证书中的一堆密钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("changgou02", "changgou02".toCharArray());

        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();


        // 创建令牌，需要私钥加盐[RSA算法]
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("nikename", "tomcat");
        payload.put("address", "bj");
        payload.put("role", "admin,user");

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));

        // 获取令牌
        String token = jwt.getEncoded();
        System.out.println(token);
    }

    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiYmoiLCJyb2xlIjoiYWRtaW4sdXNlciIsIm5pa2VuYW1lIjoidG9tY2F0In0.DZTteO4wDJdWN_H1t5Z0CA6qjD1rmezQFjhO2HuFHFL6tVuJYMQ_AAIsmhbAYzLPv_jvXCZWKYf8v2Yxa1PQ1xaQ-89RfClYy_NUHiX9nA5uKh7tFu2ipxbbkOWWYGwr39KuwdlUpkDqXrv2PZ8hRm4hKd9cxEY5wjXSMIOOKWjAkAKOYqavF_XPZ-E-C5jqzj5oM-_L6AwhSPdc9xaRz873Jl8Bc6cDHiT4I9AfFc3giYYyej6q3hImI5WShCe8TPV9pHsMdcYlwC7sAIYWXvKnf8exMYyRsFK2P1uCAaFkbIklLemAiExVjOQF8FK22nFZCQ9bsfTHEGOaFpALBw";

        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkLkdpVP2eEbiunonM4mDkIC7QlruQql6O8bAO/QOnnWu8NCQQlSjbvyk/igqJ+dA4hOVJivhurf5IsNsxpKXz7J4spYAT0vxXSrRlSmaCzm1e6DyWIi86WLCY3xeRx5/hdnXuq9DqyhlOlSwFaHTrKBLPAFAIyM5Wpa6aBmxVhOBW46qzMuH5pADunIEOV1h4CQ9YaSpE/IoYYVMCbQBkePBcT0v0k9dTnPKbsDDr1mLHKEykpaBtdTH5tdBSD2uoJCNp9z+YBbfCX2aT+esdkUMBQlmJjvb5pxUUl1mEEqBTZFt+DIkytD1EMUXEB9RHfzrD9fm0SJZi6FwJG+B4wIDAQAB-----END PUBLIC KEY-----";

        Jwt jwt = JwtHelper.decodeAndVerify(token,
                new RsaVerifier(publickey)
        );
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
