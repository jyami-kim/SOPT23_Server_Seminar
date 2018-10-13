package org.sopt.seminar2.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class firstController {
//    @GetMapping("")
//    public String hello(){
//        return "hello world!";
//    }
    @RequestMapping(method = RequestMethod.GET, value = "")
    public String hello2(){
        return "Nice to Meet you!";
    }
}
