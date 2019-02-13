package org.sopt.seminar5.service;

import org.sopt.seminar5.dto.User;
import org.sopt.seminar5.mapper.UserMapper;
import org.sopt.seminar5.model.DefaultRes;
import org.sopt.seminar5.model.LoginReq;
import org.sopt.seminar5.model.SignUpReq;
import org.sopt.seminar5.utils.ResponseMessage;
import org.sopt.seminar5.utils.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;

    private final JwtService jwtService;

    public AuthService(final UserMapper userMapper, JwtService jwtService){
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public DefaultRes<JwtService.TokenRes> login(final LoginReq loginReq){
        final User user = userMapper.findByNameAndPassword(loginReq.getName(), loginReq.getPassword());
        if(user !=null){
            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}
