package com.st.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    private static String SIGNATURE;

    public JWTUtils(String signature) {
        this.SIGNATURE = signature;
    }

    /**
     * 签发Token
     *
     * @Author zhizhizhi
     * @Create 2021.4.1
     *
     * @param map 想要存储在payload字段中的信息
     *
     * @return Token 签发的Token
     */
    public static String getToken(Map<String,String> map) {

        //定义token过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,30);//默认3天过期

        //使用JWT签发token
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGNATURE));
        return token;
    }

    /**
     * 验证Token
     *
     * @Author zhizhizhi
     * @Create 2021.4.1
     *
     * @param token 要验证的token信息
     *
     */
    public static void verifyToken(String token) {
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取Token信息
     *
     * @Author zhizhizhi
     * @Create 2021.4.1
     *
     * @param token token值
     *
     * @return verify 获取解析后token对象
     */
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
}
