package org.sopt.serminar3_sopt2.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.serminar3_sopt2.model.DefaultRes;
import org.sopt.serminar3_sopt2.service.UserService2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TimeController {
    private final UserService2 userService;
    public TimeController(UserService2 userService){
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity getTime(){
        String inDate   = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String inTime   = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());

        return new ResponseEntity(inDate+inTime, HttpStatus.OK);
    }
}
