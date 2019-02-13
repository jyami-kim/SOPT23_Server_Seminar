package org.sopt.seminar5.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;

import org.sopt.seminar5.dto.User;
import org.sopt.seminar5.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
public class JwtService {

    @Value("${JWT.ISSUER}")
    private String ISSUER;

    @Value("${JWT.SECRET}")
    private String SECRET;

    private UserMapper userMapper;

    //토큰 생성
    public String create(final int user_idx){
        try{
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("user_idx", user_idx);
            return b.sign(Algorithm.HMAC256(SECRET));
        }catch (JWTCreationException jwtCreationException){
            log.info(jwtCreationException.getMessage());
        }
        return null;
    }

    //토큰 해독
    public Token decode(final String token) {
        try {
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return new Token(decodedJWT.getClaim("user_idx").asLong().intValue());

        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new Token();
    }

    public int isUser(final String header, final int userIdx) { //userIdx 내가 수정하려는!!
        int curIdx = decode(header).getUser_idx();
        if (curIdx != -1) {
            if (curIdx == userIdx) {
                return 1;
            }
        }
        return 0;
    }

    public static class Token{
        private int user_idx = -1;

        public Token(){}

        public Token(final int user_idx){
            this.user_idx = user_idx;
        }
        public int getUser_idx(){
            return this.user_idx;
        }
    }

    public static class TokenRes{
        private String token;

        public TokenRes(){}
        public TokenRes(final String token){
            this.token = token;
        }
        public String getToken(){
            return token;
        }
        public void setToken(String token){
            this.token = token;
        }
    }
}


