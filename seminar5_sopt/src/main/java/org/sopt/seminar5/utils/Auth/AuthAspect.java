package org.sopt.seminar5.utils.Auth;

import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sopt.seminar5.dto.User;
import org.sopt.seminar5.mapper.UserMapper;
import org.sopt.seminar5.model.DefaultRes;
import org.sopt.seminar5.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class AuthAspect {
    private final static String AUTHORIZATION = "Authorization";

    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().status(401).message("인증실패").build();
    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final HttpServletRequest httpServletRequest;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    public AuthAspect(final HttpServletRequest httpServletRequest, final UserMapper userMapper, final JwtService jwtService){
        this.httpServletRequest = httpServletRequest;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    // 토큰 유효성 검사
    @Around("@annotation(org.sopt.seminar5.utils.Auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable{
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);
        if(jwt == null) return RES_RESPONSE_ENTITY;
        final JwtService.Token token = jwtService.decode(jwt);
        if(token == null){
            return RES_RESPONSE_ENTITY;
        }else{
            final User user = userMapper.findByUserIdx(token.getUser_idx());
            if(user == null) return  RES_RESPONSE_ENTITY;
            return pjp.proceed(pjp.getArgs());
        }

    }

}
