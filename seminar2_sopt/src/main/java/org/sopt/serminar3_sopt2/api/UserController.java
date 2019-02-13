package org.sopt.serminar3_sopt2.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.serminar3_sopt2.model.DefaultRes;
import org.sopt.serminar3_sopt2.model.User;

import org.sopt.serminar3_sopt2.service.UserService2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//    // 전체 User 조회
//    DefaultRes<List<User>> findAll();
//    //userIdx로 User조회
//    DefaultRes<User> findByUserIdx(final int userIdx);
//    //User 저장
//    DefaultRes save(final User user);
//    //User 수정
//    DefaultRes<User> update (final int userIdx, final User user);
//    //userIdx로 User 삭제
//    DefaultRes deleteByUserIdx (final int userIdx);

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService2 userService;

    public UserController(final UserService2 userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity findUsers
            (@RequestParam(value = "name", defaultValue = "") final String name,
             @RequestParam(value = "part", defaultValue = "") final String part) { //findAll , findByName , findByPart

        if (name.equals("") && part.equals("")) {
            log.info("get All Users");
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } else {
            if (!name.equals("") && !part.equals("")) {
                log.info("get User by part and name");
                return new ResponseEntity(userService.findByPartAndName(part, name), HttpStatus.OK);
            } else if (name.equals("")) { //part만 있을 때
                log.info("get User by part");
                return new ResponseEntity(userService.findByPart(part), HttpStatus.OK);

            } else { //name만 있을 때
                log.info("get User by name");
                return new ResponseEntity(userService.findByName(name), HttpStatus.OK);
            }
        }
    }


    @GetMapping("/{useridx}")
    public ResponseEntity findUser(@PathVariable(value = "useridx") final int useridx) {
        log.info("get User by idx");
        return new ResponseEntity(userService.findByUserIdx(useridx), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody User user) {
        log.info("save Users" + user.toString());
        return new ResponseEntity(userService.save(user), HttpStatus.OK);
    }

    @PutMapping("/{useridx}")
    public ResponseEntity update(@PathVariable(value = "useridx") final int useridx,
                                 @RequestBody User user) {
        log.info("update User, index :" + useridx);
        return new ResponseEntity(userService.update(useridx, user), HttpStatus.OK);
    }

    @DeleteMapping("/{useridx}")
    public ResponseEntity deleteByUserIdx(@PathVariable(value = "useridx") final int useridx) {
        log.info("delete user, index : " + useridx);
        return new ResponseEntity(userService.deleteByUserIdx(useridx), HttpStatus.OK);
    }

}
