package org.sopt.seminar5.service;

import lombok.extern.slf4j.Slf4j;
import org.sopt.seminar5.dto.User;
import org.sopt.seminar5.mapper.UserMapper;
import org.sopt.seminar5.model.DefaultRes;


import org.sopt.seminar5.model.SignUpReq;

import org.sopt.seminar5.utils.ResponseMessage;
import org.sopt.seminar5.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;

    private final S3FileUploadService s3FileUploadService;

    /**
     * UserMapper 생성자 의존성 주입
     *
     * @param userMapper
     */
    public UserService(final UserMapper userMapper, S3FileUploadService s3fileUploadService) {
        this.userMapper = userMapper;
        this.s3FileUploadService = s3fileUploadService;
    }


   //모든회원 조회
    public DefaultRes getAllUsers() {
        final List<User> userList = userMapper.findAll();
        if (userList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, userList);
    }

    //이름으로 회원조회
    public DefaultRes findByName(final String name) {
        final User user = userMapper.findByName(name);
        if (user == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
    }

    //회원가입
    @Transactional
    public DefaultRes save(SignUpReq signUpReq) {
        try {
            if(signUpReq.getProfile() != null)
                signUpReq.setProfileUrl(s3FileUploadService.upload(signUpReq.getProfile()));
            userMapper.save(signUpReq);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_USER);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    //회원정보 수정
    @Transactional
    public DefaultRes update(final SignUpReq signUpReq, final int userIdx) {
        User temp = userMapper.findByUserIdx(userIdx);
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        try {
            if (signUpReq.getName() != null) temp.setName(signUpReq.getName());
            if (signUpReq.getPart() != null) temp.setPart(signUpReq.getPart());
            if(signUpReq.getProfileUrl() != null) temp.setProfileUrl(signUpReq.getProfileUrl());
            userMapper.update(userIdx, temp);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.UPDATE_USER);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    //회원 탈퇴
    @Transactional
    public DefaultRes deleteByUserIdx(final int userIdx) {
        final User user = userMapper.findByUserIdx(userIdx);
        if (user == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);

        try {
            userMapper.deleteByUserIdx(userIdx);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_USER);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}