package org.sopt.serminar3_sopt2.service.impl;

import org.sopt.serminar3_sopt2.model.DefaultRes;
import org.sopt.serminar3_sopt2.model.User;
import org.sopt.serminar3_sopt2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final List<User> userList = new LinkedList<>();

    public DefaultRes<List<User>> findAll(){
        if(userList.isEmpty()){
            return DefaultRes.res(HttpStatus.NOT_FOUND.value(), "리스트가 비었습니다.");
        }
        return DefaultRes.res(HttpStatus.OK.value(), "리스트 조회 성공", userList);
    }

    @Override
    public DefaultRes<User> findByUserIdx(int userIdx) {
        for(User u: userList){
            if(u.getUserIdx() == userIdx){
                return DefaultRes.res(HttpStatus.OK.value(), "UserIdx로 User 찾기 성공", u);
            }
        }
        return DefaultRes.res(HttpStatus.NOT_FOUND.value(), "UserIdx로 User 찾기 실패");
    }

    @Override
    public DefaultRes save(User user) {
        userList.add(user);
        return DefaultRes.res(HttpStatus.OK.value(), "User 회원가입 성공");
    }

    @Override
    public DefaultRes<User> update(int userIdx, User user) {
        for(User u: userList){
            if(u.getUserIdx()== userIdx){
                userList.remove(u);
                userList.add(u);
                return DefaultRes.res(HttpStatus.OK.value(), "회원정보 수정 완료");
            }
        }
        return DefaultRes.res(HttpStatus.NOT_FOUND.value(), "회원을 찾을 수 없습니다.");
    }

    @Override
    public DefaultRes deleteByUserIdx(int userIdx) {
        for(User u: userList){
            if(u.getUserIdx()== userIdx){
                userList.remove(u);
                return DefaultRes.res(HttpStatus.OK.value(), "회원정보 삭제 완료");
            }
        }
        return DefaultRes.res(HttpStatus.NOT_FOUND.value(), "회원을 찾을 수 없습니다.");
    }
}
