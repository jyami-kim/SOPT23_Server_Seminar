package org.sopt.seminar5.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.seminar5.dto.User;
import org.sopt.seminar5.model.DefaultRes;
import org.sopt.seminar5.service.AuthService;
import org.sopt.seminar5.service.JwtService;
import org.sopt.seminar5.service.UserService;
import org.sopt.seminar5.model.SignUpReq;
import org.sopt.seminar5.utils.Auth.Auth;
import org.sopt.seminar5.utils.ResponseMessage;
import org.sopt.seminar5.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.sopt.seminar5.model.DefaultRes.FAIL_DEFAULT_RES;
@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthService authService;

    public UserController(final UserService userService, final JwtService jwtService, final AuthService authService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @GetMapping("")
    public ResponseEntity getUser(@RequestParam("name") final Optional<String> name) {
        try {
//            log.info("ID: "+ jwtService.decode(header));
            //name이 null일 경우 false, null이 아닐 경우 true
            if(name.isPresent()) return new ResponseEntity<>(userService.findByName(name.get()), HttpStatus.OK);
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity signup(SignUpReq signUpReq, @RequestPart(value = "profile", required = false) final MultipartFile profile) {
        try {
            //파일을 SignUpReq에 저장
            if(profile != null)
                signUpReq.setProfile(profile);
            return new ResponseEntity<>(userService.save(signUpReq), HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Auth
    @PutMapping("/{userIdx}")
    public ResponseEntity updateUser(
            @RequestHeader("Authorization") final String header,
            @PathVariable(value = "userIdx") final int userIdx,
            SignUpReq signUpReq, @RequestPart(value="profile", required=false) final MultipartFile profile) {
        try {
            if(jwtService.isUser(header, userIdx) == 1){ //user가 맞을 때.
                if(profile != null)
                    signUpReq.setProfile(profile);
                return new ResponseEntity<>(userService.update(signUpReq, userIdx), HttpStatus.OK);
            }
            return new ResponseEntity(new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.NOT_CURRENT_USER), HttpStatus.OK);

        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userIdx}")
    public ResponseEntity deleteUser(@PathVariable(value = "userIdx") final int userIdx) {
        try {
            return new ResponseEntity<>(userService.deleteByUserIdx(userIdx), HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}