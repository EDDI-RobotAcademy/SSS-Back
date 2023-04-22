package com.example.demo.domain.utility;

import com.example.demo.domain.security.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class TokenBasedController {

    @Autowired
    private RedisService redisService;

    protected Long findMemberId(HttpServletRequest request) {
        // Bearer = OAuth 2.0 인증 프로토콜에서 사용하는 인증 토큰의 유형을 명시하는 문자열
        // Vue에서 Bearer 다음에 오는 토큰 값은 하나의 문자열로 인식되어야 하기 때문에, Bearer와 토큰 값 사이에는 한 칸 띄어쓰기가 필요
        String maybeToken = request.getHeader("Authorization");

        if (maybeToken != null && maybeToken.startsWith("Bearer ")) {
            String token = maybeToken.substring(7); // "Bearer " 이후의 문자열을 추출
            Long redisMemberId = redisService.getValueByKey(token);

            if (redisMemberId == null) {
                return null;
            }

            return redisMemberId;
        } else {
            return null;
        }
    }
}