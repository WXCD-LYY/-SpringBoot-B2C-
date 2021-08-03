package com.changgou.oauth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/3/8/008 18:27
 **/
public class AdminToken {

    /**
     * 管理员令牌发放
     * @return
     */
    public static String adminToken() {

        //访问证书路径 读取jks的文件
        ClassPathResource resource = new ClassPathResource("changgou01.jks");

        //创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "changgou01".toCharArray());

        //读取秘钥对(公钥、私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("changgou01", "changgou01".toCharArray());

        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();

        //自定义Payload
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("nikename", "tomcat");
        tokenMap.put("address", "sz");
        tokenMap.put("authorities", new String[]{"admin","oauth"});

        //生成Jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivate));


        //取出令牌
        String token = jwt.getEncoded();
        System.out.println(token);
        return token;
    }
}

